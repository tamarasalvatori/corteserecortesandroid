package com.example.atelie_corteserecortes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.atelie_corteserecortes.db.Budget
import com.example.atelie_corteserecortes.db.BudgetDAO

@Database(entities = [Budget::class], version = 1, exportSchema = true)
abstract class MyDatabase :
    RoomDatabase() { // Esta classe é abstrata porque a Room é quem cria a sua implementação para nós.
    abstract val budgetDAO: BudgetDAO // Neste aplicativo temos somente um DAO. Nada impede de termos vários DAOs e várias tabelas

    companion object { // O companion Object permite acessar métodos sem instanciar a classe (equivalente ao Static)
        @Volatile
        private var INSTANCE: MyDatabase? = null
        /* @Volatile: A anotação Volatile garante que o valor da variável INSTANCE esteja sempre atualizado e que permaneça mesmo para todos
        os threads de execução. Com o @Volatile, o valor da variável INSTANCE nunca será cacheado e todas as escritas e leituras serão realizadas na memória principal
        o que significa que as alterações feitas por uma thread no INSTANCE, serão imediatamente visíveis para todos as outras Threads */

        /* INSTANCE irá manter a referência ao seu banco de dados uma vez que você o tiver criado. Isso eveitará que criemos conexções repetidas
        com o banco de dados (o que tem um custo computacional substancial) */
        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                /* synchronized(this): Como várias threads podem potencialmente solicitar uma instância de banco de dados ao mesmo tempo.
                Envolvendo o código em um bloco Síncrono, apenas um thread de execução por vez pode entrar neste bloco de código, o que garante
                que o banco de dados seja inicializado apenas uma vez.*/
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database" // ALTERAR CONFORME PROJETO
                    ).build()
                }
                return instance
            }
        }
    }
}