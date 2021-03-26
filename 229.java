import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


import java.io.*;
import java.util.*;

class cspringer
{
    static class node
    {
        String name;
        String aff;
        String mail;
        node(String a,String b,String c)
        {
            name=a;
            aff=b;
            mail=c;
        }
    }
    static boolean breakMap(HashMap<String,node> map,String x)
    {
        x=x.replace(" ","").replace(".","").toLowerCase();
        ArrayList<String> al=new ArrayList<String>(map.keySet());
        for(String i:al)
        {
            if(x.contains(i))
                return true;
        }
        return false;
    }
    public static void main( String[] args ) throws IOException
    {
        String fileName = args[0];
        PDDocument document = null;
        try
        {
            document = PDDocument.load( new File(fileName));
            PDFTextStripper stripper = new PDFTextStripper()
            {
                float prevBaseFont = 0;
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException
                {
                    StringBuilder builder = new StringBuilder();
                    for (TextPosition position : textPositions)
                    {
                        float baseFont = position.getFontSizeInPt();
                        if (baseFont != 0 && baseFont!=prevBaseFont)
                        {
                            builder.append('[').append(baseFont).append("ivdjkvvndkvfnkdfki]");
                            prevBaseFont = baseFont;
                        }
                        builder.append(position.getUnicode());
                    }
                    writeString(builder.toString());
                }
            };
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            stripper.setSortByPosition(true);
            String pdfText = stripper.getText(document).toString();
            // System.out.println(pdfText);
            String parsed[]=pdfText.split("\n");
            int zzz=-1;
            int zz=1;
            while(zzz<=document.getNumberOfPages() && zzz==-1)
            {
                stripper.setStartPage(zz);
                stripper.setEndPage(zz);
                pdfText = stripper.getText(document).toString();
                parsed=pdfText.split("\n");
                for(int i=0;i<parsed.length;i++)
                {
                    if(parsed[i].replace(" ","").toLowerCase().contains("abstract") || parsed[i].replace(" ","").toLowerCase().contains("keywords") || parsed[i].replace(" ","").toLowerCase().contains("indexterm"))
                    {
                        zzz=zz;
                        break;
                    }
                }
                zz++;
            }
            stripper.setSortByPosition(true);
            for(int i=0;i<parsed.length;i++)
            {
                parsed[i]=parsed[i].replace("\r", "");
                parsed[i]=parsed[i].trim();
            }
            String title="",authName="",aff="",email="";
            int c=0;
            int i=0;
            if(parsed[0].contains("©"))
                i=1;
            int fio=parsed[i].lastIndexOf(']');
            while(parsed[i].substring(fio+1).trim().length()==0 || parsed[i].replace(" ","").charAt(parsed[i].lastIndexOf(']')+1)<65 || parsed[i].replace(" ","").charAt(parsed[i].lastIndexOf(']')+1)>90)
            {
                i++;
                fio=parsed[i].lastIndexOf(']');
            }
            title+=parsed[i].substring(fio+1);
            i++;
            int flagx=0;
            for(int j=i;;j++)
            {
                if(!(parsed[j].charAt(0)=='['))
                {
                    title+=" "+parsed[j];
                    i=j;
                    flagx=1;
                }
                else
                    break;
            }
            System.out.println("title: "+title.replace("[ivdjkvvndkvfnkdfki]",""));
            i++;
            fio=parsed[i].lastIndexOf(']');
            while(parsed[i].substring(fio+1).trim().length()==0 || parsed[i].replace(" ","").charAt(parsed[i].lastIndexOf(']')+1)<65 || parsed[i].replace(" ","").charAt(parsed[i].lastIndexOf(']')+1)>90)
            {
                i++;
                fio=parsed[i].lastIndexOf(']');
            }
            authName=parsed[i++].substring(fio+1);
            while(!parsed[i].replace(" ","").toLowerCase().contains("abstract") && !parsed[i].replace(" ","").toLowerCase().contains("introduction"))
            {
                i++;
                authName+=parsed[i]+" ";
            }
            String authors[]=authName.replace("and",",").split(",");
            HashMap<String,node> map=new HashMap<>();
            for(int j=0;j<authors.length;j++)
            {
                String key="";
                authors[j]=authors[j].trim();
                String au[]=authors[j].split(" ");
                for(int k=0;k<au.length-1;k++)
                    key+=au[k].charAt(0);
                key+=au[au.length-1];
                map.put(key.toLowerCase(),new node(authors[j],"",""));
            }
            stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            stripper.setSortByPosition(true);
            pdfText = stripper.getText(document).toString();
            // System.out.println(pdfText);
            parsed=pdfText.split("\n");
            for(int j=0;j<parsed.length;j++)
            {
                String pr=parsed[j].replace("\r", "").trim();
                if(pr.length()!=0)
                    parsed[j]=pr;
            }
            String abs="";
            while(!parsed[i].replace(" ","").toLowerCase().contains("abstract"))
                i++;
            while(!parsed[i].replace(" ","").toLowerCase().contains("keywords") && !parsed[i].replace(" ","").toLowerCase().contains("indexterm"))
            {
                abs+=parsed[i]+" ";
                i++;
            }
            String key="";
            while(!breakMap(map,parsed[i]))
            {
                key+=parsed[i]+" ";
                i++;
            }
            int lt=i;
            while(!parsed[i].contains("©"))
                i++;
            String e="",af="";
            for(int j=i-1;j>=lt;j--)
            {
                if(parsed[j].contains("@"))
                    e=parsed[j];
                else if(map.containsKey(parsed[j].replace(".","").replace(" ","").toLowerCase()) || j==lt)
                {
                    node save;
                    String ke="";
                    if(j==lt)
                        ke=parsed[j].replace("(✉)","").split("⋅")[0].replace(".","").replace(" ","").toLowerCase();
                    else
                        ke=parsed[j].replace(".","").replace(" ","").toLowerCase();
                    save=map.get(ke);
                    save.mail=e;
                    save.aff=af;
                    map.put(ke,save);
                    af="";
                    continue;
                }
                else
                    af=parsed[j]+af;
            }
            ArrayList<String> al=new ArrayList<String>(map.keySet());
            int ci=1;
            for(String j:al)
            {
                System.out.println();
                System.out.println("Author "+ci+": "+map.get(j).name.trim());
                int pos=map.get(j).mail.indexOf('@');
                int start=0;
                for(int k=pos;k>=0;k--)
                {
                    if(map.get(j).mail.charAt(k)==' ')
                        start=k;
                }
                if(map.get(j).mail.trim().length()==0)
                    map.get(j).mail="null";
                System.out.println("Email "+ci+": "+map.get(j).mail.substring(start).trim());
                if(map.get(j).aff.trim().length()==0)
                    map.get(j).aff="null";
                System.out.println("Affiliation "+ci+": "+map.get(j).aff.trim());
                ci++;
            }
            System.out.println();
            System.out.println(abs);
            System.out.println();
            System.out.println(key);
        }
        catch(Exception e)
        {
            System.out.println("Unable to Process the given document further. Please check the template of the format which you have chosen or send us the pdf from My Profile route for review/bug fix.");
            return;
        }
        finally
        {
            if( document != null )
            {
                document.close();
            }
        }
    }
}
