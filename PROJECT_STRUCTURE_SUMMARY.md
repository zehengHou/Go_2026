# 📦 Go_2026 项目结构总览

## 🏗️ 模块架构图

```
┌─────────────────────────────────────────────────────────────────┐
│                         app (Android App)                       │
│                    Presentation Layer (UI)                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ MainActivity.kt, UserViewModel.kt                        │   │
│  └──────────────────────────────────────────────────────────┘   │
└────────┬──────────┬─────────────┬──────────┬────────────────────┘
         │          │             │          │
         ▼          ▼             ▼          ▼
    ┌────────┐ ┌────────┐  ┌───────────┐ ┌──────┐
    │ domain │ │  data  │  │android_   │ │utils │
    │(JVM Lib)│ │(And Lib)│  │utils      │ │(JVM) │
    │        │◄┤        │  │(And Lib)  │ │      │
    └────────┘ └────────┘  └─────┬─────┘ └──────┘
                                  │          ▲
                                  └──────────┘
    ┌──────────┐
    │  kt_lib  │
    │ (JVM Lib)├──────────────────►utils
    └──────────┘
```

## 📊 模块总览

| 模块 | 类型 | 职责 | 依赖 |
|------|------|------|------|
| **app** | Android App | UI层，用户交互 | domain, data, android_utils, utils |
| **domain** | JVM Library | 业务逻辑核心 | 无（最内层） |
| **data** | Android Library | 数据访问实现 | domain |
| **utils** | JVM Library | 通用工具（纯JVM） | 无 |
| **android_utils** | Android Library | Android工具 | utils |
| **kt_lib** | JVM Library | Kotlin学习代码 | utils |

## 🎯 Clean Architecture 三层结构

### 1️⃣ Domain Layer（核心业务层）
**模块**: `domain` (Pure JVM Library)

```
domain/
├── model/
│   ├── User.kt              # 用户实体
│   └── Result.kt            # 通用结果封装
├── repository/
│   └── UserRepository.kt    # 仓库接口
└── usecase/
    ├── GetUsersUseCase.kt   # 获取用户列表
    └── GetUserByIdUseCase.kt # 获取用户详情
```

**特点**:
- ✅ 不依赖任何其他模块
- ✅ 不包含 Android SDK
- ✅ 定义业务规则和接口
- ✅ 完全可测试

### 2️⃣ Data Layer（数据访问层）
**模块**: `data` (Android Library)

```
data/
├── datasource/
│   └── MockUserDataSource.kt    # Mock数据源（1000条数据）
└── repository/
    └── UserRepositoryImpl.kt    # 仓库实现
```

**特点**:
- ✅ 实现 Domain 层接口
- ✅ 依赖 domain 模块
- ✅ 负责数据获取
- ✅ 模拟网络延迟（1秒）

### 3️⃣ Presentation Layer（表现层）
**模块**: `app` (Android Application)

```
app/
├── presentation/
│   └── UserViewModel.kt         # ViewModel
└── MainActivity.kt              # Compose UI
```

**特点**:
- ✅ 依赖 domain 和 data
- ✅ 使用 StateFlow 管理状态
- ✅ Compose UI
- ✅ 显示 1000 条用户数据

## 🔗 依赖关系详解

### Clean Architecture 核心依赖
```
app → domain ← data
```

### 完整依赖图
```
app
├── domain (业务逻辑)
├── data (数据访问)
├── android_utils (Android工具)
└── utils (通用工具)

data
└── domain

android_utils
└── utils

kt_lib
└── utils
```

## 📁 完整文件列表

### Domain Layer (5个文件)
```
✓ com/example/domain/model/User.kt
✓ com/example/domain/model/Result.kt
✓ com/example/domain/repository/UserRepository.kt
✓ com/example/domain/usecase/GetUsersUseCase.kt
✓ com/example/domain/usecase/GetUserByIdUseCase.kt
```

### Data Layer (2个文件)
```
✓ com/example/data/datasource/MockUserDataSource.kt (1000条数据)
✓ com/example/data/repository/UserRepositoryImpl.kt
```

### Presentation Layer (3个核心文件)
```
✓ com/example/go_2026/MainActivity.kt (Compose UI)
✓ com/example/go_2026/presentation/UserViewModel.kt
✓ com/example/go_2026/MainViewModel.kt
```

## 🎨 Mock 数据说明

### 数据规模
- **总数**: 1000 条用户数据
- **姓氏池**: 30 个常见姓氏
- **名字池**: 40 个常见名字
- **年龄范围**: 18-60 岁
- **邮箱格式**: user1@example.com ~ user1000@example.com

### 示例数据
```
User(id=1, name="张伟", email="user1@example.com", age=25)
User(id=2, name="李伟", email="user2@example.com", age=42)
...
User(id=1000, name="萧琳", email="user1000@example.com", age=55)
```

## 📊 项目统计

| 指标 | 数量 |
|------|------|
| 总模块数 | 6 个 |
| JVM Library | 3 个 (domain, utils, kt_lib) |
| Android Library | 2 个 (data, android_utils) |
| Android App | 1 个 (app) |
| Domain 文件 | 5 个 |
| Data 文件 | 2 个 |
| App 文件 | 6 个 |
| Mock 数据 | 1000 条 |

## ✅ Clean Architecture 原则验证

### 1. 依赖规则 ✅
- 外层依赖内层
- Domain 不依赖任何模块
- Data 只依赖 Domain
- App 依赖 Domain 和 Data

### 2. 独立性 ✅
- Domain 完全独立，可单独测试
- 业务逻辑与 UI 和数据源解耦

### 3. 单一职责 ✅
- UseCase: 一个用例一个类
- Repository: 专注数据访问抽象
- ViewModel: 专注 UI 状态管理

### 4. 可测试性 ✅
- 所有层都可以独立测试
- 通过接口可以轻松 Mock

## 🚀 运行流程

```
1. 用户打开应用
   ↓
2. UserViewModel.loadUsers()
   ↓
3. GetUsersUseCase.invoke()
   ↓
4. UserRepository.getUsers()
   ↓
5. UserRepositoryImpl.getUsers()
   ↓
6. MockUserDataSource.fetchUsers() (延迟1秒)
   ↓
7. 返回 1000 条用户数据
   ↓
8. ViewModel 更新 StateFlow
   ↓
9. Compose UI 自动重组显示列表
```

## 📖 相关文档

- `PROJECT_ARCHITECTURE.md` - 整体架构说明
- `CLEAN_ARCHITECTURE.md` - Clean Architecture 详解
- `PROJECT_STRUCTURE_SUMMARY.md` - 本文档

---
**生成时间**: 2026-02-24
**架构模式**: Clean Architecture
**开发语言**: Kotlin
**UI 框架**: Jetpack Compose
