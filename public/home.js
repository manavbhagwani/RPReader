var msg=document.querySelector(".msg");
msg.classList.add("togg");
var vert = 'SAVE'.split("").join("<br/>")
$('#vert').html(vert);

var vert1 = 'DISCARD'.split("").join("<br/>")
$('#vert1').html(vert1);

btn=document.querySelector("#sub");
btn.addEventListener("click",(e)=>{
    var flag=0;
    e.preventDefault();
    let ext=document.querySelector("input[type=file]");
    var formdata = new FormData();
    formdata.append("pdf",ext.files[0]);
    formdata.append("format",document.querySelector("select").value);
    if(ext.files.length!=1)
        flag=-1;
    if(ext.files.length==1)
        send();
    async function send(){
        const res=await axios.post('/', formdata, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        if(!(res.data=="PDF Only!"))
            document.querySelector("textarea").value=res.data;
        ext.value = "";
        btn.disabled=false;
    }
    if(flag==-1)
        btn.disabled=false;
});


document.querySelector("#vert").addEventListener("click",(e)=>{
    e.preventDefault();
    if(document.querySelector("textarea").value=="")
    {
        document.querySelector("#vert").disabled=false;
        msg.classList.remove("togg");
        msg.textContent="Nothing to upload!";
        setTimeout(function(){
            msg.classList.add("togg");
        }, 5000)
    }
    else if(confirm('Do you really want to save the data?'))
    {
        send();
        async function send(){
            const res=await axios.post('/files', {
                text:document.querySelector("textarea").value,
                end:"1"
            });
            if(res.data=="Success" || res.data=="Something went wrong" || res.data=="File contents already in the database")
            {
                document.querySelector("textarea").value="";
                msg.classList.remove("togg");
                msg.textContent=res.data;
                setTimeout(function(){
                    msg.classList.add("togg");
                }, 5000)
            }
            else
                window.location.href = '/login';
            document.querySelector("#vert").disabled=false;
        }
    }
    else
        document.querySelector("#vert").disabled=false;
});

document.querySelector("#vert1").addEventListener("click",(e)=>{
    e.preventDefault();
    document.querySelector("textarea").value="";
});
