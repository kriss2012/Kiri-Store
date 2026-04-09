package zed.rainxch.core.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_5_6 =
    object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS seen_repos (
                    repoId INTEGER NOT NULL PRIMARY KEY,
                    repoName TEXT NOT NULL DEFAULT '',
                    repoOwner TEXT NOT NULL DEFAULT '',
                    repoOwnerAvatarUrl TEXT NOT NULL DEFAULT '',
                    repoDescription TEXT,
                    primaryLanguage TEXT,
                    repoUrl TEXT NOT NULL DEFAULT '',
                    seenAt INTEGER NOT NULL
                )
                """.trimIndent(),
            )
        }
    }
