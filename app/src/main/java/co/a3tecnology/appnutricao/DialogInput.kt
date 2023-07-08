package co.a3tecnology.appnutricao

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_input.view.*


class DialogInput : DialogFragment() {

   lateinit var dialogListener: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

         return activity?.let {
             val builder = AlertDialog.Builder(it)
             val inflater = requireActivity().layoutInflater

             val view = inflater.inflate(R.layout.dialog_input, null)
             builder.setView(view)
                 .setPositiveButton(R.string.save) { dialog, which ->
                    save(

                        view.txtValue.text.toString().toInt(),
                        view.txtTGoal.text.toString().toInt()
                    )
                 }
                 .setNegativeButton(R.string.cancel) { dialog, which ->
                        getDialog()?.cancel()
                     }
             builder.create()

         } ?: throw IllegalArgumentException("erro")
    }

    private fun save(value: Int, goal: Int) {

        val data = hashMapOf(
            "value" to value,
            "goal" to goal
        )

        val bd = FirebaseFirestore.getInstance()
        bd.collection("datas")
            .document(tag!!)
            .set(data)
            .addOnSuccessListener { documentReference ->
                dialogListener.invoke()
                println("ID : $documentReference")
            }
            .addOnFailureListener { e ->
                println("error ${e.message}")
            }
    }
}