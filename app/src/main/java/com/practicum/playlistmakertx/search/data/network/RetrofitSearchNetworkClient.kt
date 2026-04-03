package com.practicum.playlistmakertx.search.data.network

import com.practicum.playlistmakertx.search.data.NetworkClient
import com.practicum.playlistmakertx.search.data.dto.Response
import com.practicum.playlistmakertx.search.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSearchNetworkClient(private val iTunseService: ITunesSearchAPI): NetworkClient {


    override fun doRequest(dto: Any): Response {
        if (dto is TrackSearchRequest) {
            val resp = iTunseService.search(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}