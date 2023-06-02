package com.e.tubesmobile.repositories

import com.benasher44.uuid.uuid4
import com.e.tubesmobile.model.JenisKomputer
import com.e.tubesmobile.model.Komputer
import com.e.tubesmobile.network.KomputerApi
import com.e.tubesmobile.persistance.KomputerDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import javax.inject.Inject

class KomputerRepository @Inject constructor(
    private val api: KomputerApi,
    private val dao: KomputerDao
) : Repository {
    suspend fun loadItems(
        onSuccess: (List<Komputer>) -> Unit,
        onError: (List<Komputer>, String) -> Unit
    ) {
        val list: List<Komputer> = dao.getList()
        api.all()
            .suspendOnSuccess {
                data.whatIfNotNull {
                    it.data?.let { list ->
                        dao.insertAll(list)
                        val items: List<Komputer> =
                            dao.getList()
                        onSuccess(items)
                    }
                }
            }
            .suspendOnError {
                onError(list, message())
            }
            .suspendOnException {
                onError(list, message())
            }
    }

    suspend fun insert(
        merk: String,
        jenis: JenisKomputer,
        harga: Int,
        dapatDiUpgrade : Boolean,
        spesifikasi : String,
        onSuccess: (Komputer) -> Unit,
        onError: (Komputer?, String) -> Unit
    ) {
        val id = uuid4().toString()
        val item = Komputer(id, merk, jenis.toString(), harga, dapatDiUpgrade, spesifikasi)
        dao.insertAll(item)
        api.insert(item)
            .suspendOnSuccess {
                onSuccess(item)
            }
            .suspendOnError {
                onError(item, message())
            }
            .suspendOnException {
                onError(item, message())
            }
    }
    suspend fun update(
        id: String,
        merk: String,
        jenis: JenisKomputer,
        harga: Int,
        dapatDiUpgrade : Boolean,
        spesifikasi : String,
        onSuccess: (Komputer) -> Unit,
        onError: (Komputer?, String) -> Unit
    ) {
        val item = Komputer(id, merk, jenis.toString(), harga, dapatDiUpgrade, spesifikasi)
        dao.insertAll(item)
        api.update(id, item)
            .suspendOnSuccess {
                onSuccess(item)
            }
            .suspendOnError {
                onError(item, message())
            }
            .suspendOnException {
                onError(item, message())
            }
    }

    suspend fun delete(id: String, onSuccess: () -> Unit,
                       onError: (String) -> Unit) {
        dao.delete(id)
        api.delete(id)
            .suspendOnSuccess {
                data.whatIfNotNull {
                    onSuccess()
                }
            }
            .suspendOnError {
                onError(message())
            }
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun find(id:String) : Komputer? {
        return dao.find(id)
    }
}