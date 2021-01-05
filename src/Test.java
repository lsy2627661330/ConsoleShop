import java.io.InputStream;
import java.util.Scanner;

public class Test {
    static int count = 0;
    static Product carts[] = new Product[3];

    public static void main(String[] args) throws ClassNotFoundException {
        boolean bool = true;
        while (bool) {
            System.out.println("请输入用户名：");
            Scanner sc = new Scanner(System.in);
            String username = sc.next();//阻塞方法

            System.out.println("请输入密码：");
            String password = sc.next();

            //File file=new File("C:\\Users\\Administrator\\IdeaProjects\\ConsoleShop\\src\\users.xlsx");
            InputStream in = Class.forName("Test").getResourceAsStream("/Users.xlsx");


            ReadUserExcel readUserExcel = new ReadUserExcel();//创建对象
            User users[] = readUserExcel.readExcel(in);

            for (int i = 0; i < users.length; i++) {
                if (username.equals(users[i].getUsername()) && password.equals(users[i].getPassword())) {
                    bool = false;
                    shopping(sc);
                    while (true) {
                        System.out.println(("查看购物车请按《1》"));
                        System.out.println(("继续购物请按《2》"));
                        System.out.println(("结账请按《3》"));
                        System.out.println(("退出请按《4》"));
                        int choose = sc.nextInt();
                        if (choose == 1) {
                            for (Product product : carts) {
                                if (product != null) {
                                    System.out.print((product.getId()));
                                    System.out.print(("\t" + product.getName()));
                                    System.out.print(("\t" + product.getPrice()));
                                    System.out.println(("\t" + product.getDesc()));
                                }
                            }
                        } else if (choose == 2) {
                            shopping(sc);
                        } else if (choose == 4) {
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("登录失败");
                }
            }
        }
    }

    public static void shopping(Scanner sc) throws ClassNotFoundException {
        InputStream inPro = Class.forName("Test").getResourceAsStream("/Product.xlsx");
        ReadProductExcel readProductExcel = new ReadProductExcel();//创建对象
        Product products[] = readProductExcel.getAllProduct(inPro);
        for (Product product : products) {
            System.out.print((product.getId()));
            System.out.print(("\t" + product.getName()));
            System.out.print(("\t" + product.getPrice()));
            System.out.println(("\t" + product.getDesc()));
        }


        System.out.println("请输入商品ID，把该商品加入购物车：");
        String pId = sc.next();
        ReadProductExcel readProductExcel1 = new ReadProductExcel();
        inPro = null;
        inPro = Class.forName("Test").getResourceAsStream("/Product.xlsx");
        Product product = readProductExcel1.getProductById(pId, inPro);
        if (product != null) {
            carts[count++] = product;
        }
    }
}