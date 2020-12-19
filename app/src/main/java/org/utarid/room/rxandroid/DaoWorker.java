package org.utarid.room.rxandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DaoWorker {
    @Query("SELECT * FROM worker")
    Maybe<List<EntityWorker>> getAllWorkers();

    @Query("SELECT * FROM worker WHERE id = :workerId")
    EntityWorker getWorkerById(int workerId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Maybe<Long> insertWorker(EntityWorker worker);

    @Delete
    Maybe<Integer> deleteWorker(EntityWorker worker);

    @Update
    void updateWorker(EntityWorker worker);
}
