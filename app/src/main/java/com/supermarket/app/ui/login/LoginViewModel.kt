package com.supermarket.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supermarket.app.data.models.User
import com.supermarket.app.data.models.UserRole
import com.supermarket.app.data.remote.FirebaseRepository
import com.supermarket.app.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: FirebaseRepository,
    private val prefs: PrefsManager
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun login(
        username: String,
        password: String,
        onResult: (success: Boolean, isAdmin: Boolean, error: String?) -> Unit
    ) {
        if (username.isBlank() || password.isBlank()) {
            onResult(false, false, "يرجى إدخال اسم المستخدم وكلمة المرور")
            return
        }
        viewModelScope.launch {
            try {
                // 1. تنظيف المدخلات بذكاء
                val cleanUsername = username.trim().lowercase().replace(" ", "")
                val cleanPassword = password.trim()
                
                // 2. معالجة البريد الإلكتروني لتجنب تكرار علامة @
                val email = if (cleanUsername.contains("@")) {
                    cleanUsername
                } else {
                    "$cleanUsername@supermarket.app"
                }

                // ① فحص الأدمن المحلي أولاً
                val local = repo.loginAdmin(cleanUsername, cleanPassword)
                if (local.isSuccess) {
                    val user = local.getOrNull()!!
                    _currentUser.value = user
                    prefs.saveUser(user)
                    onResult(true, user.role == UserRole.ADMIN, null)
                    return@launch
                }
                
                // ② تسجيل الدخول عبر Firebase باستخدام البيانات المنظفة
                val fb = repo.loginWithEmailPassword(email, cleanPassword)
                if (fb.isSuccess) {
                    val user = fb.getOrNull()!!
                    _currentUser.value = user
                    prefs.saveUser(user)
                    onResult(true, user.role == UserRole.ADMIN, null)
                } else {
                    onResult(false, false, "بيانات الدخول غير صحيحة، تأكد من الاسم وكلمة المرور")
                }
            } catch (e: Exception) {
                onResult(false, false, "حدث خطأ في الاتصال: ${e.message}")
            }
        }
    }

    fun isLoggedIn(): Boolean = prefs.isLoggedIn()
}
