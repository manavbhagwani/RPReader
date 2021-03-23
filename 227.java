import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


import java.io.*;
import java.util.*;

class cieee
{
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
                if(!parsed[i].replace(" " ,"").toUpperCase().contains("ABSTRACT"))
                {
                    parsed[i]=parsed[i].replace("\r", "");
                    if(parsed[i].trim().length()!=0)
                        parsed[i]=parsed[i].trim();
                }
                else
                    break;
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
            System.out.println();
            if(flagx==1)
                i++;
            while(!parsed[i].replace(" " ,"").toUpperCase().contains("ABSTRACT"))
            {
                c++;
                fio=parsed[i].lastIndexOf(']');
                authName=parsed[i].substring(fio+1);
                i++;
                for(int j=i;j<parsed.length && !parsed[i].replace(" ","").toLowerCase().contains("abstract");j++)
                {
                    if(!(parsed[j].contains("@")))
                    {
                        fio=parsed[j].lastIndexOf(']');
                        aff+=parsed[j].substring(fio+1)+" ";
                        i=j;
                    }
                    else
                        break;
                }
                if(parsed[i].replace(" ","").toLowerCase().contains("abstract"))
                {
                    int d=1;
                    String authors[]=authName.replace(",","and").split("and");
                    for(int j=0;j<authors.length;j++)
                        if(authors[j].trim().length()!=0)
                            System.out.println("Author "+(d++)+": "+authors[j].replace("[ivdjkvvndkvfnkdfki]","").trim());
                    System.out.println();
                    break;
                }
                i++;
                fio=parsed[i].lastIndexOf(']');
                email=parsed[i].substring(fio+1);
                fio=parsed[i].lastIndexOf('@');
                for(int j=fio;j>=0;j--)
                {
                    if(email.charAt(j)!=' ')
                        fio=j;
                    else
                        break;
                }
                email=parsed[i].substring(fio);
                i++;
                System.out.println("Author "+c+": "+authName.replace("[ivdjkvvndkvfnkdfki]",""));
                System.out.println("Affiliation "+c+": "+aff.replace("[ivdjkvvndkvfnkdfki]",""));
                System.out.println("Email "+c+": "+email.replace("[ivdjkvvndkvfnkdfki]",""));
                System.out.println();
                title="";authName="";aff="";email="";
            }
            PDFTextStripper stripper1 = new PDFTextStripper();
            stripper1.setStartPage(zzz);
            stripper1.setEndPage(zzz);
            String pdfText1 = stripper1.getText(document).toString();
            // System.out.println(pdfText1);
            parsed=pdfText1.split("\n");
            String abs="",key="";
            while((!parsed[i].replace(" " ,"").toUpperCase().contains("INTRODUCTION") && !parsed[i].replace(" " ,"").toUpperCase().contains("KEYWORD") && !parsed[i].replace(" " ,"").toUpperCase().contains("INDEXTERMS") && !parsed[i].replace(" " ,"").toUpperCase().contains("BACKGROUND")) || (parsed[i].replace(" " ,"").toUpperCase().contains("ABSTRACT")))
            {
                parsed[i]=parsed[i].trim();
                abs+=parsed[i++]+" ";
            }
            System.out.println(abs.replace("[ivdjkvvndkvfnkdfki]",""));
            System.out.println();
            while(!parsed[i].replace(" " ,"").toUpperCase().contains("INTRODUCTION") && !parsed[i].replace(" " ,"").toUpperCase().contains("BACKGROUND"))
            {
                parsed[i]=parsed[i].trim();
                key+=parsed[i++]+" ";
            }
            System.out.println(key.replace("[ivdjkvvndkvfnkdfki]",""));
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
