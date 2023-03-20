package Model

data class Product (
        var product_id:Int,
        var user_id:Int,
        var productname:String,
        var category:String,
        var expdate:String,
        var quantity: String,
        var productpic: String,
        var status: String
)