public class Main {
    double h = 0.00001;
    double e = 0.07;

    double function(double x, double y) {
        return 10*x*x+12*x*y+10*y*y;
    }

    double dfdx(double x, double y) {
        return (function(x + h, y) - function(x, y)) / h;
    }

    double dfdy(double x, double y) {
        return (function(x, y + h) - function(x, y)) / h;
    }

    double dfdx2(double x, double y) {
        return (function(x + (2 * h), y) - (2 * function(x + h, y)) + function(x, y)) / (h * h);
    }

    double dfdy2(double x, double y) {
        return (function(x, y + (2 * h)) - (2 * function(x, y + h)) + function(x, y)) / (h * h);
    }

    double dfdx3(double x, double y) {
        return ((function(x + 3 * h, y) - (3 * function(x + 2 * h, y)) + (3 * function(x + h, y)) - function(x, y)) / (h * h * h));
    }

    double dfdy3(double x, double y) {
        return ((function(x, y + 3 * h) - (3 * function(x, y + 2 * h)) + (3 * function(x, y + h)) - function(x, y)) / (h * h * h));
    }

    double[] tabxy(double x, double y) {
        return new double[]{dfdx(x, y), dfdy(x, y)};
    }

    double liczPoX(double xy, double epsilon, double a, double b) {
        double oldX = 0;
        double xn = 0;
        if (dfdx(a, xy) * dfdx(b, xy) < 0) {
            if (dfdx3(a,xy) >= 0 && dfdx(a,xy) >= 0 || dfdx3(a,xy) < 0 && dfdx(a,xy) < 0){
                xn = a;
            }
            else{
                xn = b;
            }
            while (!(Math.abs(dfdx(xn,xy)) <= epsilon || Math.abs(xn - oldX) <= epsilon)) {
                oldX = xn;
                xn = oldX - (dfdx(oldX, xy) / dfdx2(oldX, xy));
            }
        }
        return xn;
    }

    double liczPoY(double xy, double epsilon, double a, double b) {
        double oldY = 0;
        double yn = 0;
        if (dfdy(a, xy) * dfdy(b, xy) < 0) {
            if (dfdy3(xy,a) >= 0 && dfdy(xy,a) >= 0 || dfdy3(xy,a) < 0 && dfdy(xy,a) < 0){
                yn = a;
            }
            else{
                yn = b;
            }
            while (!(Math.abs(dfdy(xy,yn)) <= epsilon || Math.abs(yn - oldY) <= epsilon)) {
                oldY = yn;
                yn = oldY - (dfdy(xy, yn) / dfdy2(xy, yn));
            }
        }
        return yn;
    }
    void oblicz( double x, double y)
    {
        double [] tab = tabxy(x,y);
        while ((Math.abs(tab[0]) >= e || Math.abs(tab[1]) >= e)) {
            x = liczPoX(y, e, -100, 100);
            y = liczPoY(x, e, -100, 100);
            tab = tabxy(x, y);
        }
        System.out.println("x = "+x + "\ny = " + y);
        System.exit(0);
    }



    public static void main(String[] args) {
        Main app = new Main();
//        app.oblicz(10,10);
    }
}
