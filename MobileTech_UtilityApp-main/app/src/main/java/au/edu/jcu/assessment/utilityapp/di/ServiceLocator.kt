package au.edu.jcu.assessment.utilityapp.di

import au.edu.jcu.assessment.utilityapp.data.remote.RetrofitClient
import au.edu.jcu.assessment.utilityapp.data.repository.WeatherRepository

object ServiceLocator {
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(RetrofitClient.apiService)
    }
}