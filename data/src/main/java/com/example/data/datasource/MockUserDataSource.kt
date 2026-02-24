package com.example.data.datasource

import com.example.domain.model.User
import kotlinx.coroutines.delay

// Mock Data Source - Simulates API or Database
class MockUserDataSource {
    
    private val surnames = listOf(
        "张", "李", "王", "刘", "陈", "杨", "赵", "黄", "周", "吴",
        "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
        "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧"
    )
    
    private val givenNames = listOf(
        "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军",
        "洋", "勇", "艳", "杰", "涛", "明", "超", "秀兰", "霞", "平",
        "刚", "桂英", "华", "建华", "文", "辉", "力", "云", "红", "鑫",
        "宇", "浩", "凯", "婷", "欣", "雨", "晨", "阳", "悦", "琳"
    )
    
    // Generate 1000 mock users
    private val mockUsers = List(1000) { index ->
        val id = index + 1
        val surname = surnames[index % surnames.size]
        val givenName = givenNames[(index / surnames.size) % givenNames.size]
        val name = "$surname$givenName"
        val email = "user${id}@example.com"
        val age = (18..60).random()
        
        User(id = id, name = name, email = email, age = age)
    }
    
    // Simulate network delay
    suspend fun fetchUsers(): List<User> {
        delay(1000) // Simulate 1 second network delay
        return mockUsers
    }
    
    // Simulate fetching a single user
    suspend fun fetchUserById(id: Int): User? {
        delay(500) // Simulate network delay
        return mockUsers.find { it.id == id }
    }
}
