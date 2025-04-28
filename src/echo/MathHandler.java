package echo;

import java.net.Socket;
import java.util.*;

public class MathHandler extends RequestHandler{
    private final static HashSet<String> VALIDOPERATORS = new HashSet<>(Arrays.asList("add", "sub", "mul", "div"));
    private double total;

    public MathHandler(Socket sock) {
        super(sock);
        total = 0.0;
    }

    public MathHandler() {
        super();
        total = 0.0;
    }

    protected String response(String request) throws Exception {
        String[] response = request.split(" ");
        String result;
        if(response.length < 2 || !VALIDOPERATORS.contains(response[0].toLowerCase())){   //invalid if not enough keywords/invalid operator
            result = "Unrecognized command: " + request;
            return result;
        }else {
            String operator = response[0].toLowerCase();
            ArrayList<Double> nums = new ArrayList<Double>();
            try {
                for (int i = 1; i < response.length; i++) {
                    nums.add(Double.parseDouble(response[i]));
                }
                if(operator.equals("add")) { total = add(nums); }
                if(operator.equals("mul")) { total = mul(nums); }
                if(operator.equals("sub")) { total = sub(nums); }
                if(operator.equals("div")) { total = div(nums); }
            } catch (ArithmeticException e) {   // divide by 0 error
                result = "Divide by 0 error: " + request;
                return result;
            } catch (Exception e) {   // generic exception catch
                result = "Unrecognized command: " + request;
                return result;
            }
        }
        result = Double.toString(total);
        return result;
    }

    private double add(ArrayList<Double> nums){ //adds all numbers together
        total = nums.getFirst();;
        for(int i = 1; i < nums.size(); i++){
            total += nums.get(i);
        }
        return total;
    }

    private double mul(ArrayList<Double> nums){ //multiplies all numbers together
        total = nums.getFirst();;
        for(int i = 1; i < nums.size(); i++){
            total *= nums.get(i);
        }
        return total;
    }

    private double sub(ArrayList<Double> nums){ //takes first number and subtracts all following numbers
        total = nums.getFirst();
        for(int i = 1; i < nums.size(); i++){
            total -= nums.get(i);
        }
        return total;
    }

    private double div(ArrayList<Double> nums) throws ArithmeticException{ //takes first number and divides by all following numbers
        total = nums.getFirst();
        for(int i = 1; i < nums.size(); i++){
            if(nums.get(i) == 0) { throw new ArithmeticException(); }
            total /= nums.get(i);
        }
        return total;
    }
}
