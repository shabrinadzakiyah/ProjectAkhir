package com.example.projectakhir

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userInfo = loadUserInfo()
        view.findViewById<TextView>(R.id.tvName).text = userInfo["name"]
        view.findViewById<TextView>(R.id.tvEmail).text = userInfo["email"]
        view.findViewById<TextView>(R.id.tvNim).text = userInfo["nim"]
        view.findViewById<TextView>(R.id.tvClass).text = userInfo["class"]

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            handleLogout()
        }

        return view
    }

    private fun loadUserInfo(): Map<String, String> {
        val fileName = "user_info.txt"
        val file = File(requireContext().filesDir, fileName)
        val userInfo = mutableMapOf<String, String>()

        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                val content = lines.toList()
                if (content.size >= 4) {
                    userInfo["name"] = content[0]
                    userInfo["email"] = content[1]
                    userInfo["nim"] = content[2]
                    userInfo["class"] = content[3]
                }
            }
        }
        return userInfo
    }

    private fun handleLogout() {
        val fileName = "user_info.txt"
        val file = File(requireContext().filesDir, fileName)
        if (file.exists()) {
            file.delete()
        }

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}