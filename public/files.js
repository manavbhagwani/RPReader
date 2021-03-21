async function getItem(){
    const res=await axios.get("download");
    // console.log(res.data);
    const paren=document.querySelector(".floa");
    for(let i=res.data.length-1;i>=0;i--)
    {
        let div=document.createElement("div");
        div.classList.add("card");
        div.classList.add("m-4");
        div.classList.add("w18rem");
        div.style.boxShadow="0 2px 8px rgba(0,0,0,.4)";
        div.style.borderRadius="5px";
        div.innerHTML="<div class=\"card-body\"><h5 class=\"card-title\"><strong>"+res.data[i].Title.trim()+" </strong></h5><p class=\"card-text\"> <cite style=\"font-size:0.8em;\">"+res.data[i].Format.toUpperCase()+"  </cite></p></div>"
        for(let j=0;j<res.data[i].Author.length;j++)
            div.innerHTML+="<ul class=\"list-group list-group-flush\"><li class=\"list-group-item\">"+res.data[i].Author[j].trim()+" </li>"
        div.innerHTML+="<div class=\"list-group-item d-flex justify-content-between align-items-center\"><a href=\""+"details/"+res.data[i]._id+"\">More Details</a><a href=\""+"downloadpdf"+res.data[i].Path+"\"target=\"_blank\"><img style=\"height:40px; width:40px;\" src=\"wordpress-pdf-icon.png\" alt=\"Download\"></a></div></ul>"
        paren.appendChild(div);
    }
    const sb=document.querySelector("#search");
    var crd=document.querySelectorAll(".w18rem");
    var prom=document.querySelector(".prom");
    let coun=0;
    if(res.data.length==0)
        prom.classList.remove("togg");
    else
    {
        sb.addEventListener("keyup",(e)=>{
            let flag=false;
            for(let i=0;i<res.data.length;i++)
            {
                if(!crd[i].textContent.toLowerCase().includes(sb.value.toLowerCase().trim()))
                    crd[i].classList.add("togg");
                else if(crd[i].classList.contains("togg"))
                {
                    if(!prom.classList.contains("togg"))
                        document.querySelector(".prom").classList.add("togg");
                    crd[i].classList.remove("togg");
                    flag=true;
                }
                else
                {
                    if(!prom.classList.contains("togg"))
                        document.querySelector(".prom").classList.add("togg");
                    flag=true;
                }
            }
            if(!flag)
                prom.classList.remove("togg");
        });
    }
};
getItem();
