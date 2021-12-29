package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.phone.base.common.CustomTypes
import com.phone.base.common.realmObject.FixtureObject
import com.phone.base.common.realmObject.FixtureShadow
import io.realm.Realm

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/1/20
 */
object FixtureDbUtils {

    fun deleteFixture(fixtureAddress: Int, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val fixtureObject = realm.where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
            fixtureObject?.deleteFromRealm()
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun deleteFixtureAsync(fixtureAddress: Int, onSuccess: Realm.Transaction.OnSuccess?,
                           onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                val fixtureObject = it.where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
                fixtureObject?.deleteFromRealm()
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteAllFixtures(isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            realm.delete(FixtureObject::class.java)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun deleteAllFixturesAsync(onSuccess: Realm.Transaction.OnSuccess?,
                               onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                it.delete(FixtureObject::class.java)
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun modifyFixtureFavorite(fixtureAddress: Int, isFavorite: Boolean, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            val fixtureObject = realm.where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
            if (fixtureObject != null) {
                if (!realm.isInTransaction) {
                    realm.beginTransaction()
                }
                fixtureObject.isFavorite = isFavorite
                realm.commitTransaction()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun modifyFixtureFavoriteAsync(fixtureAddress: Int, isFavorite: Boolean) {
        try {
            Realm.getDefaultInstance().executeTransactionAsync {
                val fixtureObject = it.where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
                fixtureObject?.isFavorite = isFavorite
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getAllFixtures(): List<FixtureObject> {
        return Realm.getDefaultInstance().where(FixtureObject::class.java).lessThan("type", CustomTypes.FixturesTypes.SCM).findAll()
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getFixture(fixtureAddress: Int): FixtureObject? {
        return Realm.getDefaultInstance().where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getFixtureType(fixtureAddress: Int): Int? {
        val fixtureObject = Realm.getDefaultInstance().where(FixtureObject::class.java).equalTo("addr", fixtureAddress).findFirst()
        return fixtureObject?.type
    }

    @MainThread
    fun getFixtureShadow(fixtureAddress: Int): FixtureShadow? {
        return Realm.getDefaultInstance().where(FixtureShadow::class.java).equalTo("addr", fixtureAddress).findFirst()
    }
}