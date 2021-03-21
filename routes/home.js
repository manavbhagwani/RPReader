const router=require('express').Router();
const multer = require('multer');
const path = require('path');
const {exec} = require('child_process');
const fs = require('fs');
const cookieParser=require('cookie-parser');
const jwt=require('jsonwebtoken');

router.use(cookieParser());

router.get("/",(req,res)=>{
    if(req.cookies==undefined)
        res.render("home",{username:"undefined"})
    else
    {
        token=req.cookies.token;
        if(token=="null" || token=="undefined")
            res.render("home",{username:"undefined"});
        else
        {
            try{
                var decode=jwt.verify(token,"msdhoni");
            }catch(Error){
                res.render("home",{username:"undefined"});
                return;
            }
            res.render("home",{username:decode.username});
        }
    }
});


const storage=multer.diskStorage({
    destination: "./uploads/",
    filename: function(req,file,cb){
        cb(null,file.fieldname + '-' + Date.now() + path.extname(file.originalname))
    }
})

const upload = multer({
  storage: storage,
  limits:{fileSize: 100000000},
  fileFilter: function(req, file, cb){
    checkFileType(file, cb);
  }
}).single('pdf');

function checkFileType(file, cb){
  const filetypes = /pdf/;
  const extname = filetypes.test(path.extname(file.originalname).toLowerCase());
  const mimetype = filetypes.test(file.mimetype);
  if(mimetype && extname){
    return cb(null,true);
  } else {
    cb('PDF Only!');
  }
}

router.post('/', (req, res) => {
  upload(req, res, (err) => {
      if(err){
          res.send("PDF Only!");
      }
      else
      {
          if(req.file == undefined)
              res.send("PDF Only!");
          else
          {
              if(req.body.format=="ieee")
              {
                  let cmd="java -cp .:pdfbox-app-2.0.22.jar cieee "+"\""+req.file.path+"\"";
                  var dir = exec(cmd, function(err, stdout, stderr) {
                    if (err) {
                        res.send(err+" "+stderr);
                    }
                    else
                        res.send(stdout+"\n"+"ieee");
                  });
              }
              else if(req.body.format=="elsevier")
              {
                  let cmd="java -cp .:pdfbox-app-2.0.22.jar celsevier "+"\""+req.file.path+"\"";
                  var dir = exec(cmd, function(err, stdout, stderr) {
                    if (err) {
                        res.send(err+" "+stderr);
                    }
                    else
                        res.send(stdout+"\n"+"elsevier");
                  });
              }
              else
              {
                  let cmd="java -cp .:pdfbox-app-2.0.22.jar cspringer "+"\""+req.file.path+"\"";
                  var dir = exec(cmd, function(err, stdout, stderr) {
                    if (err) {
                        res.send(err+" "+stderr);
                    }
                    else
                        res.send(stdout+"\n"+"springer");
                  });
              }
          }
      }
  });
});


module.exports=router;
