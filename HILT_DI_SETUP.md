# 🔧 Dagger Hilt 依赖注入配置说明

## 📦 已完成的配置

### ✅ 1. 添加 Hilt 依赖

**libs.versions.toml**:
```toml
[versions]
hilt = "2.51"

[libraries]
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }

[plugins]
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
kotlin-kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
```

**app/build.gradle.kts**:
```kotlin
plugins {
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
```

### ✅ 2. Application 类

**GoApplication.kt**:
```kotlin
@HiltAndroidApp
class GoApplication : Application()
```

**AndroidManifest.xml**:
```xml
<application
    android:name=".GoApplication"
    ...>
```

### ✅ 3. Hilt 模块

#### DataModule - 提供数据层依赖
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideMockUserDataSource(): MockUserDataSource
    
    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: MockUserDataSource
    ): UserRepository
}
```

#### DomainModule - 提供业务逻辑依赖
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    
    @Provides
    @Singleton
    fun provideGetUsersUseCase(
        repository: UserRepository
    ): GetUsersUseCase
    
    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(
        repository: UserRepository
    ): GetUserByIdUseCase
}
```

### ✅ 4. ViewModel 注入

**Before (手动注入)**:
```kotlin
class UserViewModel : ViewModel() {
    private val dataSource = MockUserDataSource()
    private val repository = UserRepositoryImpl(dataSource)
    private val getUsersUseCase = GetUsersUseCase(repository)
    private val getUserByIdUseCase = GetUserByIdUseCase(repository)
}
```

**After (Hilt 注入)**:
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel()
```

### ✅ 5. Activity 注入

**MainActivity.kt**:
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
}
```

## 🎯 依赖注入流程

```
1. Application 启动
   ↓
2. @HiltAndroidApp 初始化 Hilt
   ↓
3. DataModule 创建单例依赖
   - MockUserDataSource (Singleton)
   - UserRepositoryImpl (Singleton)
   ↓
4. DomainModule 创建单例依赖
   - GetUsersUseCase (Singleton)
   - GetUserByIdUseCase (Singleton)
   ↓
5. MainActivity 启动
   @AndroidEntryPoint 注入
   ↓
6. UserViewModel 创建
   @HiltViewModel 自动注入 UseCases
   ↓
7. 所有依赖自动管理
```

## 📊 Hilt 组件作用域

| 组件 | 作用域 | 生命周期 |
|------|--------|---------|
| **SingletonComponent** | @Singleton | Application 生命周期 |
| **ActivityComponent** | @ActivityScoped | Activity 生命周期 |
| **ViewModelComponent** | @ViewModelScoped | ViewModel 生命周期 |
| **FragmentComponent** | @FragmentScoped | Fragment 生命周期 |

本项目使用 **SingletonComponent**，所有依赖都是应用级单例。

## 🔍 关键注解说明

### @HiltAndroidApp
- 用于 Application 类
- 触发 Hilt 代码生成
- 必须在 Application 中使用

### @AndroidEntryPoint
- 用于 Activity、Fragment、View、Service、BroadcastReceiver
- 启用依赖注入
- 本项目用于 MainActivity

### @HiltViewModel
- 用于 ViewModel
- 自动创建 ViewModel 工厂
- 支持构造函数注入

### @Module
- 定义 Hilt 模块
- 提供依赖对象

### @InstallIn
- 指定模块安装到哪个组件
- SingletonComponent = 应用级单例

### @Provides
- 提供依赖的方法
- 用于无法使用 @Inject 构造函数的类

### @Inject
- 标记构造函数进行注入
- 用于 ViewModel 构造函数

## 🆚 Before vs After

### Before (手动注入)
```kotlin
// ViewModel 内部手动创建所有依赖
class UserViewModel : ViewModel() {
    private val dataSource = MockUserDataSource()
    private val repository = UserRepositoryImpl(dataSource)
    private val getUsersUseCase = GetUsersUseCase(repository)
    // ...
}
```

**缺点**:
- ❌ ViewModel 依赖创建逻辑
- ❌ 难以测试（无法 Mock）
- ❌ 违反依赖倒置原则
- ❌ 代码耦合度高

### After (Hilt 注入)
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel()
```

**优点**:
- ✅ 依赖外部注入
- ✅ 易于测试（可以 Mock）
- ✅ 符合 SOLID 原则
- ✅ 代码解耦
- ✅ 自动管理生命周期

## 🧪 测试优势

使用 Hilt 后，可以轻松替换依赖进行测试：

```kotlin
// 测试时可以提供 Mock 实现
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestDataModule {
    @Provides
    fun provideMockUserDataSource(): MockUserDataSource {
        return FakeUserDataSource() // 测试用的假数据
    }
}
```

## 📁 文件结构

```
app/
├── GoApplication.kt          ✅ @HiltAndroidApp
├── MainActivity.kt           ✅ @AndroidEntryPoint
├── presentation/
│   └── UserViewModel.kt      ✅ @HiltViewModel
└── di/
    ├── DataModule.kt         ✅ 数据层依赖
    └── DomainModule.kt       ✅ 业务层依赖
```

## 🚀 使用方式

### 添加新的依赖

1. **创建类时使用 @Inject 构造函数**:
```kotlin
class NewUseCase @Inject constructor(
    private val repository: UserRepository
) {
    // ...
}
```

2. **在 Module 中提供（如果不能用 @Inject）**:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NewModule {
    @Provides
    fun provideNewDependency(): NewDependency {
        return NewDependencyImpl()
    }
}
```

3. **在 ViewModel 中注入**:
```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val newUseCase: NewUseCase
) : ViewModel()
```

## ✅ 配置验证清单

- ✅ Hilt 版本: 2.51
- ✅ kapt 插件已启用
- ✅ Application 类添加 @HiltAndroidApp
- ✅ AndroidManifest.xml 指向 GoApplication
- ✅ DataModule 提供数据层依赖
- ✅ DomainModule 提供业务层依赖
- ✅ UserViewModel 添加 @HiltViewModel 和 @Inject
- ✅ MainActivity 添加 @AndroidEntryPoint
- ✅ 所有依赖使用 @Singleton 作用域

## 🎓 学习要点

1. **依赖注入三要素**:
   - 依赖 (Dependency): 需要使用的对象
   - 注入点 (Injection Point): 需要依赖的地方
   - 提供者 (Provider): 提供依赖的模块

2. **Hilt 工作原理**:
   - 编译时代码生成
   - 自动创建依赖图
   - 管理对象生命周期

3. **Clean Architecture + DI**:
   - Domain 层不知道 Hilt
   - Data 层不知道 Hilt
   - 只在 Presentation 层使用 Hilt
   - 完美的关注点分离

---
**配置完成时间**: 2026-02-24  
**依赖注入框架**: Dagger Hilt 2.51  
**架构模式**: Clean Architecture + Dependency Injection
