package com.golkoyhaber.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

private data class News(
    val category: String,
    val title: String,
    val summary: String,
    val source: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NewsAdapter
    private val allNews = listOf(
        News("Gölköy / Ordu Gündem", "GölköyHaber yayın sistemi hazır", "Gölköy ve Ordu kaynaklarını tek haber akışında birleştirmek için temel uygulama hazırlandı.", "GölköyHaber"),
        News("Resmî Kurumlar", "Resmî kurum duyuruları", "Kaymakamlık, belediye ve diğer kamu kurumlarından doğrulanabilir duyurular bu bölümde gösterilecek.", "Kaynak motoru"),
        News("Sağlık", "Sağlık haberleri", "Ordu ve Gölköy bölgesindeki doğrulanabilir sağlık duyuruları için ayrı kategori.", "Kaynak motoru"),
        News("Spor", "Gölköy ve Ordu spor", "Yerel kulüpler, okul sporları ve Ordu spor kaynakları için ayrı akış.", "Kaynak motoru"),
        News("Tarım", "Fındık ve tarım", "Fındık, üretici duyuruları ve tarım kurumlarından gelen haberler.", "Kaynak motoru"),
        News("Vefat & Taziye", "Vefat ve taziye duyuruları", "Hassas içerikler ayrı kategoride, kaynak ve tarih bilgisi korunarak yayınlanacak.", "Gölköy Vefat")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(allNews)
        findViewById<RecyclerView>(R.id.newsList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        buildCategories()

        findViewById<Button>(R.id.adminButton).setOnClickListener { showSourceInfo() }
        findViewById<Button>(R.id.facebookButton).setOnClickListener { openFacebookPage() }
    }

    private fun buildCategories() {
        val bar = findViewById<LinearLayout>(R.id.categoryBar)
        val categories = listOf("Tümü", "Gölköy", "Resmî", "Asayiş", "Eğitim", "Sağlık", "Spor", "Tarım", "Siyaset", "Hava", "Vefat")
        categories.forEach { category ->
            val button = MaterialButton(this).apply {
                text = category
                setOnClickListener {
                    val filtered = when (category) {
                        "Tümü" -> allNews
                        "Gölköy" -> allNews.filter { it.category.startsWith("Gölköy") }
                        "Resmî" -> allNews.filter { it.category == "Resmî Kurumlar" }
                        "Vefat" -> allNews.filter { it.category == "Vefat & Taziye" }
                        else -> allNews.filter { it.category.contains(category, ignoreCase = true) }
                    }
                    adapter.submit(filtered)
                    findViewById<TextView>(R.id.pageTitle).text = if (category == "Tümü") "Gölköy / Ordu Gündem" else category
                }
            }
            bar.addView(button)
        }
    }

    private fun showSourceInfo() {
        AlertDialog.Builder(this)
            .setTitle("GölköyHaber kaynak merkezi")
            .setMessage(
                "Hedef Facebook Sayfası:\n" +
                "facebook.com/habergolkoy52\n\n" +
                "Mimari:\n" +
                "Kaynaklar → Haber Motoru → Filtre/Mükerrer Kontrol → Editör Onayı → APK + Web + Facebook Sayfası\n\n" +
                "Yeni kaynaklar sonradan backend üzerinden eklenebilecek şekilde tasarlanmıştır."
            )
            .setPositiveButton("Tamam", null)
            .show()
    }

    private fun openFacebookPage() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/habergolkoy52")))
    }
}

private class NewsAdapter(private var items: List<News>) : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): NewsViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size
    fun submit(newItems: List<News>) { items = newItems; notifyDataSetChanged() }
}

private class NewsViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
    fun bind(news: News) {
        itemView.findViewById<TextView>(R.id.category).text = news.category.uppercase()
        itemView.findViewById<TextView>(R.id.title).text = news.title
        itemView.findViewById<TextView>(R.id.summary).text = news.summary
        itemView.findViewById<TextView>(R.id.source).text = "Kaynak: ${news.source}"
    }
}
