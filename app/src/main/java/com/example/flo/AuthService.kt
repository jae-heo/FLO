package com.example.flo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView : LoginView
    private lateinit var autoLoginView: AutoLoginView
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setAutoLoginView(autoLoginView: AutoLoginView){
        this.autoLoginView = autoLoginView
    }

    fun setLoginView(loginView : LoginView){
        this.loginView = loginView
    }
    fun signUp(user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        signUpView.onSignUpLoading()
        authService.signUp(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUPACT/API-RESPONSE", response.toString())
                val resp = response.body()!!
                Log.d("SIGNUPACT/FLO", resp.toString())
                when (resp.code) {
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(resp.code, resp.message)
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUPACT/API-ERROR", t.toString())
                signUpView.onSignUpFailure(400, "네트워크 오류")
            }
        })
    }

    fun login(user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        loginView.onLoginLoading()
        authService.login(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Login/API-RESPONSE", response.toString())
                val resp = response.body()!!
                Log.d("Login/FLO", resp.toString())
                when (resp.code) {
                    1000 -> loginView.onLoginSuccess(resp.result!!)
                    else -> loginView.onLoginFailure(resp.code, resp.message)
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUPACT/API-ERROR", t.toString())
                loginView.onLoginFailure(400, "네트워크 오류")
            }
        })
    }

    fun autoLogin(jwt : String){
        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        autoLoginView.onAutoLoginLoading()
        authService.autoLogin(jwt).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("AutoLogin/API-RESPONSE", response.toString())
                val resp = response.body()!!
                Log.d("AutoLogin/FLO", resp.toString())
                when (resp.code) {
                    1000 -> autoLoginView.onAutoLoginSuccess(resp.result!!)
                    else -> autoLoginView.onAutoLoginFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AutoLoginACT/API-ERROR", t.toString())
                autoLoginView.onAutoLoginFailure(400, "네트워크 오류")
            }
        })
    }
}