package au.edu.jcu.assessment.utilityapp.data.repository

import au.edu.jcu.assessment.utilityapp.data.model.WeatherResponse
import au.edu.jcu.assessment.utilityapp.data.remote.ApiService

class WeatherRepository(private val api: ApiService) {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): WeatherResponse {
        return api.getCurrentWeather(latitude, longitude)
    }
}