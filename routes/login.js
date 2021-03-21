const router=require('express').Router();
const bcrypt = require('bcrypt');
const user=require('../models/userSchema');
const mongoose=require('mongoose');
const jwt=require('jsonwebtoken');

router.get("/",(req,res)=>{
    res.render("login");
});
router.post("/",(req,res)=>{
    user.find({email:req.body.email},function(err,data){
        if(err)
            res.send("incorrect");
        else
        {
            if(data.length==0)
                res.send("incorrect");
            else
            {
                bcrypt.compare(req.body.password, data[0].password, function(err, result) {
                    if(result)
                    {
                        const token = jwt.sign({ email: req.body.email, username:data[0].name }, 'msdhoni',{expiresIn:'30d'});
                        const farFuture = new Date(new Date().getTime() + (1000*60*60*24*30));
                        res.cookie("token",token,{ expires: farFuture, httpOnly: true });
                        res.send("successful");
                    }
                    else
                    {
                        res.send("incorrect");
                    }
                });
            }
        }
    });
});

module.exports=router
