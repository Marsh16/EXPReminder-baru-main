package Database

import Model.Product
import Model.User
import Model.Userconfirmcheck
import android.util.Base64

class GlobarVar {
    companion object{
        val STORAGE_PERMISSION_CODE: Int = 100
        val listUser = ArrayList<User>()//simpan sementara user karena  kotlin.UninitializedPropertyAccessException: lateinit property user has not been initialized
        val listUserc = ArrayList<Userconfirmcheck>()//simpan sementara confirm

        val listProduct = ArrayList<Product>()
        val listProductEXP = ArrayList<Product>()
        val listProductGood = ArrayList<Product>()
        val listProductEdit = ArrayList<Product>()//simpan sementara product

        val ipAddress = "http://192.168.43.106/ExpReminder/"
        val ReadAllProductById = ipAddress + "ReadAllProductByUser_ID.php"
        //val ReadAllProductByStatusExp = ipAddress + "ReadAllProductByStatusExp.php"
        //val ReadAllProductByStatusGood = ipAddress + "ReadAllProductByStatusGood.php"
        val CreateProduct = ipAddress + "CreateProduct.php"
        val CreateUser = ipAddress + "CreateUser.php"
        val DeleteProduct = ipAddress + "DeleteProduct.php"
        val DeleteUser = ipAddress + "DeleteUser.php"
        val CheckUser = ipAddress + "CekUser.php"
        val UpdateStatus = ipAddress + "updatestatus.php"
        val UpdateStatusGood = ipAddress + "updatestatusgood.php"
       // val NotExp = ipAddress + "ReadAllProductByStatusGood.php"
       // val Exp = ipAddress + "ReadAllProductByStatusExp.php"
        val ReadUserByUsername = ipAddress + "ReadUserByUsername.php"
        val CheckUserExist = ipAddress + "CekUserExist.php"
        val CheckProductExist = ipAddress + "CekProductExist.php"
       // val ReadAllProduct = ipAddress + "ReadAllProduct.php"
        //val ReadAllUser = ipAddress + "ReadAllUser.php"
        val ReadProductByID = ipAddress + "ReadProductByID.php"
        val ReadUserByID = ipAddress + "ReadUserByID.php"
        val UpdateProduct = ipAddress + "UpdateProduct.php"
        val UpdateUser = ipAddress + "UpdateUser.php"

        fun ByteArrToString(bArray: ByteArray): String {
            return Base64.encodeToString(bArray, Base64.DEFAULT)
        }

        fun StringToByteArr(raw: String):ByteArray{
            return Base64.decode(raw, Base64.DEFAULT)
        }
    }
}