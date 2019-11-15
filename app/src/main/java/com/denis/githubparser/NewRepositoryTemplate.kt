package com.denis.githubparser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.denis.githubparser.databinding.ActivityNewRepositoryTemplateBinding
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil

class NewRepositoryTemplate : AppCompatActivity() {
    private lateinit var binding: ActivityNewRepositoryTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_repository_template)

        binding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.editName.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = binding.editName.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
