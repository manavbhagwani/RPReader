const mongoose=require('mongoose');

const userSchema = new mongoose.Schema({
   Format:String,
   Title:String,
   Author:[String],
   Affiliation:[String],
   Email:[String],
   Abstract:String,
   Keyword:String,
   Path:String
});
var data = mongoose.model("data",userSchema);

module.exports=data
