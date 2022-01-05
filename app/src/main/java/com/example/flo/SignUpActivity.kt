package com.example.flo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity :  AppCompatActivity(), SignUpView {
    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupSinginIv.setOnClickListener {
            signUp()
            finish()
        }

    }

    private fun getUser() : User{
        val email : String = binding.signupIdEt.text.toString() + "@" + binding.signupEmailEt.text.toString()
        val pwd : String = binding.signupPasswordEt.text.toString()
        val name : String = binding.signupNameCheckEt.text.toString()
        return User(email,pwd,name)
    }



//    private fun signUp(){
//        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
//            Toast.makeText(this,"아이디를 정확하게 입력해주세요.", Toast.LENGTH_SHORT).show()
//        }
//
//        if(binding.signupPasswordEt.text.toString() !=  binding.signupPasswordCheckEt.text.toString()){
//            Toast.makeText(this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
//        }
//
//        if(binding.signupPasswordEt.text.toString().isEmpty() ||  binding.signupPasswordCheckEt.text.toString().isEmpty()){
//            Toast.makeText(this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//        }
//
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val users = userDB.userDao().getUsers()
//
//        Log.d("signup", users.toString())
//    }

    private fun signUp(){
        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
            Toast.makeText(this,"아이디를 정확하게 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

        if(binding.signupPasswordEt.text.toString() !=  binding.signupPasswordCheckEt.text.toString()){
            Toast.makeText(this,"비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        if(binding.signupPasswordEt.text.toString().isEmpty() ||  binding.signupPasswordCheckEt.text.toString().isEmpty()){
            Toast.makeText(this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }




    override fun onSignUpLoading() {

    }

    override fun onSignUpSuccess() {
        Toast.makeText(this,"가입되었습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        when(code){
            2016,2017 -> {
                Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}