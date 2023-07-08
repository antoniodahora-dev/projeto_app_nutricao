package co.a3tecnology.appnutricao

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.a3tecnology.appnutricao.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        refresh()
        imgCalories()
        imgCarbon()
        imgProtein()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                refresh()
            }

            else -> {
                DialogInput().apply {
                    dialogListener = {
                        refresh()
                    }
                    show(supportFragmentManager, item.title.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun imgCalories() {
        binding.cardView2.setOnClickListener {
          val dialog = DialogInput()
            dialog.dialogListener = {
                refresh()
            }
            dialog.show(supportFragmentManager, getString(R.string.calories))
        }
    }

    private fun imgCarbon() {
        binding.cardView.setOnClickListener {
            val dialog = DialogInput()
            dialog.dialogListener = {
                refresh()
            }
            dialog.show(supportFragmentManager, getString(R.string.carbohydrate))
        }
    }

    private fun imgProtein() {
        binding.cardView3.setOnClickListener {
            val dialog = DialogInput()
            dialog.dialogListener = {
                refresh()
            }
            dialog.show(supportFragmentManager, getString(R.string.protein))
        }
    }

    private fun refresh() {

        binding.progress.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection("datas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val value = document.data["value"] as Long
                    val goal = document.data["goal"] as Long

                    val res = value.toFloat() * 100 / goal.toFloat()
                    val text = "$value / $goal"

                    when(document.id) {
                        getString(R.string.calories) -> {
                            binding.txtCalories.text = text
                           binding.progress1.setValue(res.toInt())
                        }

                        getString(R.string.protein) -> {
                            binding.txtProt.text = text
                            binding.progress2.setValue(res.toInt())
                        }

                        getString(R.string.carbohydrate) -> {
                            binding.txtCarbo.text = text
                            binding.progress3.setValue(res.toInt())
                        }

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Teste" , exception.message, exception)
                println("error")
            }
            .addOnCompleteListener {
                binding.progress.visibility = View.GONE
            }
    }
}