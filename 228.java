// Punit Paper.pdf
// Preparation_and_physical_properties_of_superhydrop.pdf
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


import java.io.*;
import java.util.*;

class celsevier
{
    public static void main( String[] args ) throws IOException
    {
        String fileName = args[0];
        PDDocument document = null;
        try
        {
            document = PDDocument.load( new File(fileName));
            PDFTextStripper stripper = new PDFTextStripper();
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
            int i=0;
            int start=0;
            int end=0;
            String parse="";
            for(String j:parsed)
            {
                if(!j.contains(".com"))
                    start++;
                else
                    break;
            }
            start++;
            i=start;
            for(int j=i;j<parsed.length;j++)
            {
                String check=parsed[j];
                check=check.replace(" ","");
                char check1[]=check.toCharArray();
                for(int k=0;k<check1.length;k++)
                    check1[k]=Character.toLowerCase(check1[k]);
                check=new String(check1);
                if(check.contains("articleinfoabstract") || check.contains("articleinfo") || check.contains("abstract"))
                    break;
                parse+=parsed[j]+"\n";
            }
            parsed=parse.split("\n");
            int titl=0;
            for(int j=parsed.length-1;j>=0;j--)
            {
                if((parsed[j].charAt(0)>=97 && parsed[j].charAt(0)<=122 && parsed[j].substring(1).trim().charAt(0)>=65 && parsed[j].substring(1).trim().charAt(0)<=90) || (parsed[j].contains("*") || parsed[j].contains("∗") || parsed[j].contains("⁎")))
                    continue;
                else
                {
                    String check[]=parsed[j].split(",");
                    int flag=0;
                    for(int k=0;k<check.length;k++)
                    {
                        check[k]=check[k].trim();
                        if(check.length>1 && check[k].charAt(check[k].length()-1)>=97 && check[k].charAt(check[k].length()-1)<=122 && check[k].charAt(0)>=65 && check[k].charAt(0)<=90)
                        {
                            flag=-1;
                            break;
                        }
                    }
                    if(flag==0)
                    {
                        titl=j;
                        break;
                    }
                }
            }
            String title="";
            for(int j=0;j<=titl;j++)
            {
                title+=parsed[j]+" ";
            }
            System.out.println("title"+": "+title);
            i=titl+1;
            int nam=i;
            for(int j=parsed.length-1;j>titl;j--)
            {
                if(parsed[j].charAt(0)>=97 && parsed[j].charAt(0)<=122 && parsed[j].substring(1).trim().charAt(0)>=65 && parsed[j].substring(1).trim().charAt(0)<=90)
                    nam=j;
            }
            nam--;
            String auth="";
            for(int j=i;j<=nam;j++)
            {
                auth+=parsed[j]+" ";
            }
            auth=auth.replace("*","").replace("∗","").replace("⁎","");
            auth=auth.replace(",,",",");
            String parsed1[]=auth.split(",");
            nam++;
            String authName="";
            int c=1;
            HashMap<Character,String> map=new HashMap<>();
            for(int j=nam;j<parsed.length;j++)
            map.put(parsed[j].charAt(0),parsed[j].substring(1).trim());
            String parsed2[]=pdfText.split("\n");
            String par[]=new String[parsed2.length];
            for(int j=0;j<par.length;j++)
                par[j]=parsed2[j].replace(" ","").toLowerCase();
            String ema="";
            int em=0;
            for(String j:par)
            {
                em++;
                if(j.contains("correspondingauthor"))
                    break;
            }
            while(parsed2[em].contains("@"))
                ema+=parsed2[em++].trim()+" ";
            int fio=ema.indexOf("@");
            for(int j=fio;j>=0;j--)
            {
                if(ema.charAt(j)!=' ' && ema.charAt(j)!=':')
                    fio=j;
                else
                    break;
            }
            ema=ema.substring(fio);
            par=ema.split(",");
            HashMap<String,String> map1=new HashMap<>();
            for(String j:par)
            {
                String key=j.substring(j.indexOf('(')+1,j.indexOf(')')).replace(".","").replace(" ","").toLowerCase();
                String value=j.substring(0,j.indexOf('(')).trim();
                map1.put(key,value);
            }
            for(int j=0;j<parsed1.length;j++)
            {
                parsed1[j]=parsed1[j].trim();
                System.out.println();
                System.out.println("Author "+c+": "+parsed1[j].substring(0,parsed1[j].length()-1).trim());
                String au=parsed1[j].substring(0,parsed1[j].length()-1).trim();
                String au1[]=au.split(" ");
                au="";
                for(int k=0;k<au1.length-1;k++)
                    au1[k]=au1[k].trim().charAt(0)+"";
                for(String k:au1)
                    au+=k;
                au=au.toLowerCase();
                System.out.println("Affiliation "+c+": "+map.get(parsed1[j].charAt(parsed1[j].length()-1)));
                System.out.println("Email "+c+": "+map1.get(au));
                c++;
            }
            stripper = new PDFTextStripper();
            stripper.setStartPage(zzz);
            stripper.setEndPage(zzz);
            pdfText = stripper.getText(document).toString();
            // System.out.println(pdfText);
            parsed=pdfText.split("\n");
            for(int j=0;j<parsed.length;j++)
            {
                parsed[j]=parsed[j].replace("\r", "");
                parsed[j]=parsed[j].trim();
            }
            int kwd=0,abs=0,intro=0;
            String prs[]=new String[parsed.length];
            String keyword="",abst="";
            for(int j=0;j<prs.length;j++)
                prs[j]=parsed[j].replace(" ","").toLowerCase();
            for(int j=0;j<prs.length;j++)
            {
                if(prs[j].contains("keyword") || prs[j].contains("keywords") || prs[j].contains("indexterms") || prs[j].contains("indexterm"))
                    kwd=j;
                if(prs[j].contains("abstract"))
                    abs=j;
                if(prs[j].contains("introduction"))
                    intro=j;
            }
            for(int j=0;j<parsed.length;j++)
            {
                if(j>=kwd && j<abs)
                    keyword+=parsed[j]+" ";
                if(j>=abs && j<intro)
                    abst+=parsed[j]+" ";
            }
            System.out.println();
            System.out.println(abst);
            System.out.println();
            System.out.println(keyword);
        }catch(Exception e)
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
