package Adapter

import Interface.CardListener
import Model.Product
import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expreminder.R
import kotlinx.android.synthetic.main.card_user.view.*
import java.text.SimpleDateFormat
import java.util.*


class ListDataRVAdapter(val listProduct: ArrayList<Product>, val cardListener: CardListener):
    RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {

    class viewHolder(val itemView: View, val cardListener1: CardListener) :
        RecyclerView.ViewHolder(itemView) {

        val name_card = itemView.nama_card
        val tanggal_card = itemView.tanggal_card
//        val daysleft_card = itemView.daysleft_card
       val color = itemView.colorcard
        val daysleft = itemView.daysleft


        fun setData(data: Product) {
            name_card.text = data.productname
            tanggal_card.text = data.expdate
            val datenow = System.currentTimeMillis()
            val date1 = Date(datenow)

            val format = SimpleDateFormat("dd/MM/yyyy")
            var dait: Date
            dait = format.parse(data.expdate);

//
//            val seminggu = 7
            val sebulan = 30
            val sehari = 0
           // val mAlertDialog = AlertDialog.Builder(this)
            //set alertdialog icon

            val hitungan =  (((dait.time - date1.time) / (1000 * 60 * 60 * 24))+1).toInt()

            if (dait.before(date1)) {
                color.getBackground().setTint(Color.RED)

                daysleft.text = ((dait.time - date1.time) / (1000 * 60 * 60 * 24)).toString() + " days"

            }else if(hitungan<sebulan && hitungan>sehari){
                color.getBackground().setTint(Color.YELLOW)
                daysleft.text = (((dait.time - date1.time) / (1000 * 60 * 60 * 24))+1).toString() + " days"
            }else{
                color.getBackground().setTint(Color.GREEN)
                daysleft.text = (((dait.time - date1.time) / (1000 * 60 * 60 * 24))+1).toString() + " days"
            }
//            daysleft_card.
            //daysleft_card.text = data.alamat
            itemView.setOnClickListener {
                cardListener1.onCardClick(data.product_id)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_user, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listProduct[position])
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }


}


