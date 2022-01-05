package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedalbumBinding

class SavedAlbumFragment: Fragment() {

    lateinit var binding: FragmentSavedalbumBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedAlbumRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val savedAlbumRVAdapter = SavedAlbumRVAdapter()
        //리스너 객체 생성 및 전달

        savedAlbumRVAdapter.setMyItemClickListener(object : SavedAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(albumId: Int) {
                songDB.albumDao().disLikeAlbum(getJwt(), albumId)
            }
        })

        binding.lockerSavedAlbumRecyclerView.adapter = savedAlbumRVAdapter

        savedAlbumRVAdapter.addAlbums(songDB.albumDao().getLikedAlbums(getJwt()) as ArrayList)

    }

    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }
}