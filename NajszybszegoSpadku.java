import java.util.ArrayList;

public class Main {
    double ep = 0.1;
    double h = 0.00001;

    ArrayList<Double> ax = new ArrayList<>();
    ArrayList<Double> ay = new ArrayList<>();

    double function(double x, double y)
    {
        return 3*y*y*y-7*x*y+x*x-y+6;
    }
    double liczPochodna(String zmienna, double x1, double y1)
    {
        if(zmienna == "x")
            return (function(x1+h, y1)-function(x1,y1))/h;
        else if(zmienna=="y")
            return (function(x1,y1+h)-function(x1,y1))/h;
        else
            return 1;
    }

    double liczPpoX(double x, double y)
    {
        return (function(x+2*h,y)-2*function(x+h,y)+function(x,y))/(h*h);
    }

    double liczPpoY(double x, double y)
    {
        return (function(x,y+2*h)-2*function(x,y+h)+function(x,y))/(h*h);
    }

    double liczPpoXY(double x, double y)
    {
        return (function(x+h,y+h)-function(x+h,y)-function(x,y+h)+function(x,y))/(h*h);
    }

    double [][] MacierdzOdw(double [][] m)
    {
        double [][] m_wynik = new double[2][2];
        double licz = 1/((m[0][0]*m[1][1])-m[0][1]*m[1][0]);
        m_wynik[0][0] = licz * m[1][1];
        m_wynik[0][1] = licz * (-m[0][1]);
        m_wynik[1][0] = licz * (-m[1][0]);
        m_wynik[1][1] = licz * m[0][0];
        return m_wynik;
    }

    double [] liczGradient(double x, double y){
        double [] wynik = new double[2];
        wynik[0] = liczPochodna("x", x, y);
        wynik[1] = liczPochodna("y", x, y);
        return wynik;
    }

    double [][] liczHessego (double x, double y)
    {
        double wynik [][] = new double[2][2];

        wynik[0][0] = liczPpoX(x,y);
        wynik[0][1] = liczPpoXY(x,y);
        wynik[1][0] = liczPpoXY(x,y);
        wynik[1][1] = liczPpoY(x,y);

        return wynik;
    }
    double [] mnoz(double a, double [] m2)
    {
        double [] wynik = new double[2];
        wynik[0] = m2[0]*a;
        wynik[1] = m2[1]*a;
        return wynik;
    }

    double [] odejmij(double [] m1, double [] m2)
    {
        double [] wynik = new double[2];
        wynik[0] = m1[0]-m2[0];
        wynik[1] = m1[1]-m2[1];
        return wynik;
    }

    double liczMacierz (double [] m)
    {
        return m[0]*m[1];
    }

    double licz_ak(double[] gr, double [][] hessego)
    {
        double licznik = Math.pow(gr[0],2)+Math.pow(gr[1],2);
        double mianownik = ((gr[0]*hessego[0][0]+gr[1]*hessego[1][0])*gr[0])+((gr[0]*hessego[0][1]+gr[1]*hessego[1][1])*gr[1]);
        return licznik/mianownik;
    }
    void Licz()
    {
        ax.add(2.0);
        ay.add(2.0);

        double [] xk = {ax.get(ax.size()-1), ay.get(ay.size()-1)};
        double [] xk1 = new double[2];
        double ak = licz_ak(liczGradient(xk[0],xk[1]),liczHessego(xk[0],xk[1]));
        xk1= odejmij(xk,mnoz(ak,liczGradient(xk[0],xk[1])));
        ax.add(xk1[0]);
        ay.add(xk1[1]);

        while(Math.abs(liczMacierz(liczGradient(xk1[0],xk1[1])))>ep && ((Math.abs(xk1[0]-ax.get(ax.size()-1))) > ep || (Math.abs(xk1[1]-ay.get(ay.size()-1))) > ep))
        {
            ax.add(xk1[0]);
            ay.add(xk1[1]);

            xk[0] = ax.get(ax.size()-1);
            xk[1] = ay.get(ay.size()-1);

            xk1= odejmij(xk,mnoz(licz_ak(liczGradient(xk[0],xk[1]),liczHessego(xk[0],xk[1])),liczGradient(xk[0],xk[1])));

        }
        System.out.printf("%.8f",ax.get(ax.size()-1));
        System.out.println();
        System.out.printf("%.8f",ay.get(ay.size()-1));
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.Licz();
    }

}
