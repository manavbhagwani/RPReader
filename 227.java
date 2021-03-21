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
                if(!parsed[i].contains("Abstract"))
                {
                    parsed[i]=parsed[i].replace("\r", "");
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
            int fio=parsed[i].indexOf(']');
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
            System.out.println("title: "+title);
            System.out.println();
            if(flagx==1)
                i++;
            while(!parsed[i].contains("Abstract") && !parsed[i].contains("ABSTRACT") && !parsed[i].contains("A b s t r a c t") && !parsed[i].contains("A B S T R A C T"))
            {
                c++;
                fio=parsed[i].indexOf(']');
                authName=parsed[i].substring(fio+1);
                i++;
                for(int j=i;;j++)
                {
                    if(!(parsed[j].contains("@")))
                    {
                        fio=parsed[j].indexOf(']');
                        aff+=parsed[j].substring(fio+1)+" ";
                        i=j;
                    }
                    else
                        break;
                }
                i++;
                fio=parsed[i].indexOf(']');
                email=parsed[i].substring(fio+1);
                fio=parsed[i].indexOf('@');
                for(int j=fio;j>=0;j--)
                {
                    if(email.charAt(j)!=' ')
                        fio=j;
                    else
                        break;
                }
                email=parsed[i].substring(fio);
                i++;
                System.out.println("Author "+c+": "+authName);
                System.out.println("Affiliation "+c+": "+aff);
                System.out.println("Email "+c+": "+email);
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
            while((!parsed[i].contains("INTRODUCTION") && !parsed[i].contains("Introduction") && !parsed[i].contains("I N T R O D U C T I O N") && !parsed[i].contains("I n t r o d u c t i o n") && !parsed[i].contains("KEYWORD") && !parsed[i].contains("Keyword") && !parsed[i].contains("K E Y W O R D") && !parsed[i].contains("K e y w o r d") && !parsed[i].contains("Index Terms") && !parsed[i].contains("INDEX TERMS") && !parsed[i].contains("Index  Terms") && !parsed[i].contains("INDEX  TERMS")) || (parsed[i].contains("Abstract") || parsed[i].contains("ABSTRACT") || parsed[i].contains("A b s t r a c t") || parsed[i].contains("A B S T R A C T")))
            {
                parsed[i]=parsed[i].trim();
                abs+=parsed[i++]+" ";
            }
            System.out.println(abs);
            System.out.println();
            while(!parsed[i].contains("INTRODUCTION") && !parsed[i].contains("Introduction") && !parsed[i].contains("I N T R O D U C T I O N") && !parsed[i].contains("I n t r o d u c t i o n"))
            {
                parsed[i]=parsed[i].trim();
                key+=parsed[i++]+" ";
            }
            System.out.println(key);
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
