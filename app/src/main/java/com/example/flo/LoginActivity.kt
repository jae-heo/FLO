package com.example.flo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginSignupTv.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.loginLoginIv.setOnClickListener {
            login()
            startMainActivity()
        }

    }
//
//    private fun login(){
//        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginEmailEt.text.toString().isEmpty()){
//            Toast.makeText(this,"이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
//        }
//
//        if(binding.loginPasswordEt.text.toString().isEmpty()){
//            Toast.makeText(this,"비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
//        }
//
//        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginEmailEt.text.toString()
//        val pwd : String = binding.loginPasswordEt.text.toString()
//
//        val songDB = SongDatabase.getInstance(this)!!
//
//        val user = songDB.userDao().getUser(email, pwd)
//
//        user?.let {
//            Log.d("Loginact/Get_User", "userId : ${user.id}, $user")
//            saveJwt(user.id)
//            saveName(user.name)
//            return
//        }
//        Toast.makeText(this,"회원정보가 없습니다.", Toast.LENGTH_SHORT).show()
//    }

    private fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginEmailEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
        }

        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this,"비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginEmailEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()
        val user = User(email, pwd, "")
        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(user)

    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveJwt(context: Context, jwt : String){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }
    private fun saveUserIdx(context: Context, userIdx : Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("userIdx", userIdx)
        editor.apply()
    }

    private fun saveName(name : String){
        val spf = getSharedPreferences("name", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("name", name)
        editor.apply()
    }

    override fun onLoginLoading() {

    }

    override fun onLoginSuccess(auth: Auth) {
        saveJwt(this, auth.jwt)
        saveUserIdx(this,auth.userIdx)
        startMainActivity()
    }

    override fun onLoginFailure(code : Int, message : String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


}