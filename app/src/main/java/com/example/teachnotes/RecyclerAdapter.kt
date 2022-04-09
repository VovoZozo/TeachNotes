package com.example.teachnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val noteTitlePreview = arrayOf(
        "d116df5",
        "36ffc75", "f5cfe78", "5b87628",
        "db8d14e", "9913dc4", "e120f96",
        "466251b"
    )

    private val noteTextPreview = arrayOf(
        "Kekshgsf", "Tsfhsfgh",
        "Keluarga", "Bisnis",
        "Keluarga", "Hutang",
        "Teknologi", "Pidana"
    )


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemPreviewNoteTitle: TextView
        var itemPreviewNoteText: TextView

        init {
            itemPreviewNoteTitle = itemView.findViewById(R.id.previewNoteTitle)
            itemPreviewNoteText = itemView.findViewById(R.id.previewNoteText)

//            itemView.setOnClickListener {
//                var position: Int = getAdapterPosition()
//                val context = itemView.context
//                val intent = Intent(context, DetailPertanyaan::class.java).apply {
//                    putExtra("NUMBER", position)
//                    putExtra("CODE", itemPreviewNoteTitle.text)
//                    putExtra("CATEGORY", itemPreviewNoteText.text)
//                }
//                context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.note_card_view, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemPreviewNoteTitle.text = noteTitlePreview[i]
        viewHolder.itemPreviewNoteText.text = noteTextPreview[i]

    }

    override fun getItemCount(): Int {
        return noteTitlePreview.size
    }
}

