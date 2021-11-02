package molina.raul.notas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import java.io.File
import kotlinx.android.synthetic.main.nota_layout.view.*

class AdaptadorNota : BaseAdapter {

    var notas = ArrayList<Nota>()
    var context: Context? = null

    constructor(context: Context?, notas: ArrayList<Nota>) : super() {
        this.notas = notas
        this.context = context
    }


    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(position: Int): Any {
        return notas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var nota = notas[p0]
        var inflator = LayoutInflater.from(context)
        var vista = inflator.inflate(R.layout.nota_layout, null)
        vista.tv_titulo_det.text = nota.titulo
        vista.tv_contenido_det.text = nota.contenido

        vista.btn_borrar.setOnClickListener {
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    private fun eliminar(titulo: String) {
        if (titulo == "") {
            Toast.makeText(context, "Error título vacío", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val archivo = File(ubicacion(), titulo + ".txt")
                archivo.delete()

                Toast.makeText(context, "Se elimino el archivo", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al eliminar ek archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): String {
        val album = File(context?.getExternalFilesDir(null), "notas")
        if (!album.exists()) {
            album.mkdir()
        }
        return album.absolutePath
    }

}