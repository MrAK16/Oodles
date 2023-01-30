package com.ias.oodles.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ias.oodles.db.model.TestModel
import com.ias.oodles.db.model.TestQuestionModel

@Database(entities = [TestQuestionModel::class], version = 1)
abstract class OodlesDB : RoomDatabase() {
    abstract fun oodlesDao(): OodlesDao
}