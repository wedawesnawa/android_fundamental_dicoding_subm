package com.example.appevents.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appevents.R
import com.example.appevents.databinding.FragmentSettingsBinding
import com.example.appevents.ui.worker.ScheduleReminder
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            ThemePreferences.isDarkMode(requireContext()).collect { isDarkMode ->
                binding.themeSwitch.isChecked = isDarkMode
            }
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                ThemePreferences.setDarkMode(requireContext(), isChecked)
                setNightMode(isChecked)
            }
        }

        val preferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isReminderActive = preferences.getBoolean("daily_reminder_active", false)
        binding.switchDailyReminder.isChecked = isReminderActive

        binding.switchDailyReminder.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean("daily_reminder_active", isChecked).apply()

            lifecycleScope.launch {
                ThemePreferences.setDailyReminder(requireContext(), isChecked)
                if (isChecked) {
                    ScheduleReminder.scheduleDailyReminder(requireContext())
                } else {
                    ScheduleReminder.cancelDailyReminder(requireContext())
                }
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_settings)

        return binding.root
    }

    private fun setNightMode(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        requireActivity().recreate()
    }
}