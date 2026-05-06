import com.practicum.playlistmakertx.search.data.NetworkClient
import com.practicum.playlistmakertx.search.data.network.ITunesSearchAPI
import com.practicum.playlistmakertx.search.data.network.RetrofitSearchNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<ITunesSearchAPI> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesSearchAPI::class.java)
    }
    single<NetworkClient> {
        RetrofitSearchNetworkClient(get())
    }
}