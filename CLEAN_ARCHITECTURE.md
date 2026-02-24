# 🏛️ Clean Architecture 实现说明

## 项目架构图

```
┌─────────────────────────────────────────────────┐
│              Presentation Layer                 │
│         app (Android Application)               │
│  ┌──────────────────────────────────────────┐   │
│  │ UI: Activity, Composables                │   │
│  │ ViewModel: UserViewModel                 │   │
│  └──────────────────────────────────────────┘   │
└──────────────┬──────────────────┬───────────────┘
               │                  │
               ▼                  ▼
       ┌───────────────┐  ┌──────────────┐
       │  Domain       │  │    Data      │
       │  (Pure JVM)   │◄─┤ (Android Lib)│
       └───────────────┘  └──────────────┘
```

## 📦 模块说明

### 1. Domain Layer (domain 模块) - Pure JVM Library
**职责**: 业务逻辑核心，完全独立

```
domain/
├── model/
│   ├── User.kt              # 业务实体
│   └── Result.kt            # 通用结果封装
├── repository/
│   └── UserRepository.kt    # 仓库接口
└── usecase/
    ├── GetUsersUseCase.kt   # 获取用户列表用例
    └── GetUserByIdUseCase.kt # 获取用户详情用例
```

**特点**:
- ✅ 不依赖任何其他模块
- ✅ 不包含 Android SDK 依赖
- ✅ 可以独立测试
- ✅ 定义接口，不关心实现

### 2. Data Layer (data 模块) - Android Library
**职责**: 数据访问实现

```
data/
├── datasource/
│   └── MockUserDataSource.kt  # Mock 数据源
└── repository/
    └── UserRepositoryImpl.kt  # 仓库实现
```

**特点**:
- ✅ 实现 Domain 层的接口
- ✅ 依赖 domain 模块
- ✅ 可以包含 Android 依赖
- ✅ 负责数据获取和转换

### 3. Presentation Layer (app 模块) - Android Application
**职责**: UI 和用户交互

```
app/
└── presentation/
    └── UserViewModel.kt       # ViewModel
MainActivity.kt                 # UI (Compose)
```

**特点**:
- ✅ 依赖 domain 和 data 模块
- ✅ 通过 ViewModel 调用 UseCase
- ✅ 使用 StateFlow 管理 UI 状态
- ✅ Compose UI 显示数据

## 🔄 数据流向

```
用户操作 → UI (Compose)
    ↓
ViewModel.loadUsers()
    ↓
GetUsersUseCase.invoke()
    ↓
UserRepository.getUsers()
    ↓
UserRepositoryImpl.getUsers()
    ↓
MockUserDataSource.fetchUsers()
    ↓
返回 List<User>
    ↓
UseCase 返回 Result.Success(users)
    ↓
ViewModel 更新 StateFlow
    ↓
UI 自动重组显示数据
```

## 🎯 Clean Architecture 原则实现

### 1. **依赖规则** ✅
```
app → domain ← data
(外层依赖内层，内层不知道外层)
```

### 2. **独立性** ✅
- Domain 层完全独立，不依赖框架
- 可以独立运行和测试

### 3. **单一职责** ✅
- **UseCase**: 一个用例一个类
- **Repository**: 专注数据访问抽象
- **ViewModel**: 专注 UI 状态管理

### 4. **可测试性** ✅
- Domain 层可以单独单元测试
- 通过接口可以轻松 Mock

## 📝 Mock 业务说明

### 业务场景：用户管理
1. **获取用户列表**: 显示所有用户
2. **查看用户详情**: 点击用户查看详细信息

### Mock 数据
```kotlin
张三, 25岁, zhangsan@example.com
李四, 30岁, lisi@example.com
王五, 28岁, wangwu@example.com
赵六, 35岁, zhaoliu@example.com
孙七, 22岁, sunqi@example.com
```

### 模拟延迟
- 获取列表: 1秒延迟（模拟网络请求）
- 获取详情: 0.5秒延迟

## 🚀 运行效果

1. **启动应用** → 显示 Loading
2. **1秒后** → 显示用户列表（5个用户）
3. **点击用户** → 显示 Loading
4. **0.5秒后** → 弹窗显示用户详情
5. **点击 Close** → 返回列表

## 🔧 依赖注入

目前使用**手动依赖注入**:
```kotlin
class UserViewModel : ViewModel() {
    private val dataSource = MockUserDataSource()
    private val repository = UserRepositoryImpl(dataSource)
    private val getUsersUseCase = GetUsersUseCase(repository)
}
```

**后续改进**: 可以集成 Hilt 或 Koin 进行自动依赖注入

## 📊 与传统 MVVM 对比

| 特性 | 传统 MVVM | Clean Architecture |
|------|-----------|-------------------|
| 分层 | View-ViewModel-Model | Presentation-Domain-Data |
| 业务逻辑 | 在 ViewModel 中 | 在 UseCase 中 |
| 数据访问 | ViewModel 直接调用 | 通过 Repository 接口 |
| 可测试性 | 中等 | 优秀 |
| 可维护性 | 中等 | 优秀 |
| 学习成本 | 低 | 中等 |

## 🎓 学习要点

1. **依赖规则**: 外层依赖内层
2. **接口隔离**: Domain 定义接口，Data 实现
3. **单一职责**: 每个类只做一件事
4. **数据流**: 单向数据流，状态驱动 UI

## 📌 下一步扩展

- [ ] 添加真实的网络请求（Retrofit）
- [ ] 添加本地数据库（Room）
- [ ] 集成依赖注入（Hilt/Koin）
- [ ] 添加单元测试
- [ ] 添加错误处理策略
- [ ] 添加数据缓存策略
