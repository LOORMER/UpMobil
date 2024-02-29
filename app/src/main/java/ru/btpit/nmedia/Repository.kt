package ru.btpit.nmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id:Long)
    fun shareById(id:Long)
    fun repos(id: Long)
    fun removeById(id: Long)
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 1,
            header = "ГПБОУ ВО БТПИТ",
            content = "15 февраля на базе 1 и 3 корпусов ГБПОУ ВО «БТПИТ» прошли торжественные митинги, посвященные 35-й годовщине со дня вывода советских войск из Республики Афганистан с поднятием государственного флага и возложением цветов к «Деревьям Памяти».\\nКаштаны были посажены на территории учебного корпуса № 1 и 3 в честь воинов-интернационалистов, которые учились в нашем техникуме.\\nСтуденты почтили память участников войн и конфликтов минутой молчания.",
            dataTime = "21 февраля в 19:12",
            isLike = false,
            amountlike = 999,
            amountrepost = 15,
            amountview = 500,
            isRepos = false
        ),
        Post(
            id = 2,
            header = "ГПБОУ ВО БТПИТ",
            content = "Преподаватель Борисоглебского техникума промышленных и информационных технологий Гребенникова Лариса Владимировна одно из занятий по дисциплине «Краеведение» со студентами 1 курсов специальностей «Дошкольное образование» и «Коррекционная педагогика в начальном образовании» провела в МБУК БГО Борисоглебском историко-художественном музее.",
            dataTime = "27 Февраля в 12:56",
            isLike = false,
            amountlike = 0,
            amountrepost = 0,
            amountview = 0,
            isRepos = false
            ),

        )

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                if (it.isLike)
                    it.amountlike--
                else
                    it.amountlike++
                it.copy(isLike = !it.isLike)
            }
        }
        data.value = posts
    }

    override fun repos(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(amountrepost = it.amountrepost + 10)
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                it.amountrepost++
                it.copy(isRepos = !it.isRepos)
            }
        }
        data.value = posts
    }
    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id}
        data.value = posts
    }
}

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id:Long) = repository.shareById(id)
    fun repos(id: Long) = repository.repos(id)
    fun removeById(id : Long) = repository.removeById(id)
}