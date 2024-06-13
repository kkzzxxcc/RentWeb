package com.mysite.rent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.rent.Member.Member;
import com.mysite.rent.Member.MemberService;

import com.mysite.rent.Car.Car;
import com.mysite.rent.Car.CarService;

import com.mysite.rent.Res.*;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;


@Controller
public class hellomain {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private ResService resService;

	// home 페이지
	@GetMapping("/home")
    public String home() {
		return "home";
    }

	// 회원 페이지
	@GetMapping("/member")
    public String memberMenu() {
        return "memberForm";
    }
	
	// 회원 등록 페이지
	@GetMapping("/member/register")
    public String regMember(Model model) {
        return "memberReg";
    }
	
	@PostMapping("/member/register")
    public String regMember(Model model, @RequestParam(value="id") String id, @RequestParam(value="password") String password, @RequestParam
    		(value="name")String name, @RequestParam(value="address") String address, @RequestParam(value="phoneNum") String phoneNum) {
		// 아이디 중복 여부 확인
	    if (memberService.existMemId(id)) {
	        model.addAttribute("errorMessage", "아이디가 이미 존재합니다. 다른 아이디를 입력해주세요.");
	        return "memberReg"; // 아이디 중복 시 다시 회원등록 페이지로 이동
	    }

		else {
	        memberService.registerMember(id, password, name, address, phoneNum);
	        return "redirect:/home"; // 회원등록 후 메인 페이지로 복귀
	    }
    }
	
	// 회원 조회 페이지
	@GetMapping("/member/Load")
	public String loadMember() {
	    return "memberLoad";
	}
	
	@PostMapping("/member/Load")
	public String loadMember(@RequestParam(value="id", required=false) String id, Model model) {
	    try {
	        if (id == null || id.isEmpty()) {
	            // 아이를 입력하지 않은 경우 전체 회원 조회
	            List<Member> members = memberService.loadMembers();
	            model.addAttribute("members", members);
	        }

			else {
	            // 아이디가 입력된 경우 해당 아이디의 회원 조회
	            Member member = memberService.findMemId(id);
	            if (member != null) {
	                model.addAttribute("member", member);
	            }

				else {
	                model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
	            }
	        }
	    }

		catch (Exception e) {
	        model.addAttribute("errorMessage", "회원 조회에 실패했습니다..");
	    }
	    return "memberLoad";
	}
	
	// 회원 수정 페이지
	@GetMapping("/member/Mod")
	public String modMember() {
	    return "memberMod";
	}
	
	@PostMapping("/member/Mod")
	public String modMember(@RequestParam(value = "id") String id, Model model) {
	    Member member = memberService.findMemId(id);
	    if (member != null) {
	        model.addAttribute("member", member);
	    }

		else {
	        model.addAttribute("errorMessage", "아이디가 존재하지 않습니다.");
	    }
	    return "memberMod";
	}

	@PostMapping("/member/update")
    public String updateMember(@RequestParam("id") String id, @RequestParam("password") String password,
                               @RequestParam("name") String name, @RequestParam("address") String address,
                               @RequestParam("phoneNum") String phoneNum, Model model) {
        try {
            memberService.updateMember(id, password, name, address, phoneNum);
            model.addAttribute("successMessage", "회원 정보가 수정되었습니다.");
        }

		catch (Exception e) {
            model.addAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
        }
        return "memberMod";
    }
	
	// 회원 삭제 페이지
	@GetMapping("/member/delete")
	public String delMember() {
	    return "memberDel";
	}
	
	@PostMapping("/member/delete")
    public String delMember(@RequestParam("id") String id, Model model) {
        try {
            if (memberService.existMemId(id)) {
                memberService.deleteMemId(id);
                model.addAttribute("successMessage", "회원이 삭제되었습니다.");
            }

			else {
                model.addAttribute("errorMessage", "아이디가 존재하지 않습니다.");
            }
        }

		catch (Exception e) {
            model.addAttribute("errorMessage", "회원 삭제에 실패했습니다.");
        }
        return "memberDel";
    }
	
	// 차량 메뉴 페이지
	@GetMapping("/car")
    public String carMenu() {
        return "carForm";
    }
	
	// 차량 등록 페이지
	@GetMapping("/car/register")
	public String regCar(Model model) {
	    return "carReg";
	}
	
	@PostMapping("/car/register")
    public String regCar(Model model, @RequestParam(value="carNumber") String carNumber, @RequestParam(value="carName") String carName, @RequestParam
    		(value="carColor")String carColor, @RequestParam(value="carSize") int carSize, @RequestParam(value="carMaker") String carMaker) {
		// 차량 중복 여부 확인
	    if (carService.existCarNumber(carNumber)) {
	        model.addAttribute("errorMessage", "차량 번호가 이미 등록되어 있습니다. 다른 차량 번호를 입력해주세요.");
	        return "carReg"; // 차량번호 중복 시 다시 차량등록 페이지로 이동
	    }

		else {
	        carService.registerCar(carNumber, carName, carColor, carSize, carMaker);
	        return "redirect:/home"; // 차량등록 후 메인 페이지로 복귀
	    }
    }
	
	// 차량 조회 페이지
	@GetMapping("/car/Load")
	public String loadCar() {
		return "carLoad";
	}
	
	@PostMapping("/car/Load")
	public String loadCar(@RequestParam(value="carNumber", required=false) String carNumber, Model model) {
	    try {
	        if (carNumber == null || carNumber.isEmpty()) {
	            // 차량번호가 입력되지 않은 경우 전체 차량 조회
	            List<Car> cars = carService.loadAllCar();
	            model.addAttribute("cars", cars);
	        }

			else {
	            // 차량번호가 입력된 경우 해당 차량의 차량 조회
	            Car car = carService.findCarNum(carNumber);
	            if (car != null) {
	                model.addAttribute("car", car);
	            }

				else {
	                model.addAttribute("errorMessage", "차량이 존재하지 않습니다.");
	            }
	        }
	    }

		catch (Exception e) {
	        model.addAttribute("errorMessage", "차량 조회에 실패했습니다..");
	    }
	    return "carLoad";
	}
	
	// 차량 수정 페이지
	@GetMapping("/car/Mod")
	public String modCar() {
		return "carMod";
	}
	
	
	@PostMapping("/car/Mod")
	public String modCar(@RequestParam(value = "carNumber") String carNumber, Model model) {
	    Car car = carService.findCarNum(carNumber);
	    if (car != null) {
	        model.addAttribute("car", car);
	    }

		else {
	        model.addAttribute("errorMessage", "차량이 존재하지 않습니다.");
	    }
	    return "carMod";
	}
	
	@PostMapping("/car/update")
    public String updateCar(@RequestParam("carNumber") String carNumber, @RequestParam("carName") String carName,
                               @RequestParam("carColor") String carColor, @RequestParam("carSize") int carSize,
                               @RequestParam("carMaker") String carMaker, Model model) {
        try {
            carService.updateCar(carNumber, carName, carColor, carSize, carMaker);
            model.addAttribute("successMessage", "차량 수정되었습니다.");
        }

		catch (Exception e) {
            model.addAttribute("errorMessage", "차량 수정에 실패했습니다..");
        }
        return "carMod";
    }
	
	
	// 차량 삭제 페이지
	@GetMapping("/car/delete")
	public String delCar() {
		return "carDel";
	}
		
	@PostMapping("/car/delete")
	public String delCar(@RequestParam(value = "carNumber") String carNumber, Model model) {
	    try {
	         if (carService.existCarNumber(carNumber)) {
	             	carService.deleteCarNum(carNumber);
	             	model.addAttribute("successMessage", "차량이 삭제되었습니다.");
	         }

	         else {
	             	model.addAttribute("errorMessage", "해당 차량이 존재하지 않습니다.");
	         }
	    }

		catch (Exception e) {
	        model.addAttribute("errorMessage", "차량 삭제에 실패했습니다..");
	    }
	      return "carDel";
	}
	
	
	
	// 예약 메뉴 페이지
	@GetMapping("/res")
    public String resMenu() {
        return "resForm";
    }
	
	// 예약 등록 페이지
	@GetMapping("/res/register1")
	public String resForm(Model model) {
		return "resReg1";
	}
	
	
	// 차량 번호와 회원 아이디 확인
    @PostMapping("/res/check")
    public String checkCarMember(@RequestParam("carNumber") String carNumber, @RequestParam("memberId") String memberId, Model model) {
        if (carService.existCarNumber(carNumber) && resService.existMemId(memberId)) {
            model.addAttribute("carNumber", carNumber);
            model.addAttribute("memberId", memberId);
            return "resReg2";
        }

		else {
            model.addAttribute("errorMessage", "차량 번호 또는 회원 정보가 존재하지 않습니다.");
            return "resReg1";
        }
    }

	// 예약 등록 페이지 2
	@PostMapping("/res/register2")
	public String regRes(@RequestParam("resNumber") String resNumber, @RequestParam("resDate") LocalDate resDate,
						 @RequestParam("useBeginDate") LocalDate useBeginDate,
						 @RequestParam("returnDate") LocalDate returnDate,
						 @RequestParam("price") double price,
						 @RequestParam("carNumber") String carNumber,
						 @RequestParam("memberId") String memberId, Model model) {
		try {
			if (resService.existResNum(resNumber)) {
				model.addAttribute("errorMessage", "이미 존재하는 예약번호입니다.");
				return "resReg2";
			}

			// 예약 등록 메소드
			resService.registerRes(resNumber, resDate, useBeginDate, returnDate, price, carNumber, memberId);

			// 예약 성공 메시지를 모델에 추가
			model.addAttribute("successMessage", "예약이 등록되었습니다.");

			// 성공 페이지로 이동
			return "resReg2";

		}

		catch (Exception e) {
			// 오류 발생 시 오류 메시지를 모델에 추가
			model.addAttribute("errorMessage", "예약 등록에 실패했습니다..");

			// 예약 등록 페이지로 복귀
			return "resReg2";
		}
	}

	// 예약 조회 페이지
	@GetMapping("/res/Load")
	public String loadRes() {
		return "resLoad";
	}

	@PostMapping("/res/Load")
	public String loadRes(@RequestParam(value="resNumber", required=false) String resNumber, Model model) {
		try {
			if (resNumber == null || resNumber.isEmpty()) {

				// 예약번호가 입력되지 않은 경우 전체 예약 조회
				List<Res> resList = resService.loadAllRes();
				model.addAttribute("resList", resList); // 모든 예약 정보를 모델에 추가
			}

			else {
				// 입력된 예약번호에 해당하는 예약 조회
				Res res = resService.findResNum(resNumber);
				if (res != null) {
					// 입력된 예약번호에 해당하는 예약 정보를 모델에 추가
					model.addAttribute("res", res);
				}

				else {
					model.addAttribute("errorMessage", "예약이 존재하지 않습니다.");
				}
			}
		}

		catch (Exception e) {
			model.addAttribute("errorMessage", "예약 조회에 실패하였습니다..");
		}
		return "resLoad";
	}


	
 	
 	// 예약 수정 페이지
 	@GetMapping("/res/Mod")
 	public String ModRes() {
 		return "resMod";
 	}
	
 	@PostMapping("/res/Mod")
	public String ModRes(@RequestParam(value = "resNumber") String resNumber, Model model) {
	    Res res = resService.findResNum(resNumber);
	    if (res != null) {
	        model.addAttribute("res", res);
	    }

		else {
	        model.addAttribute("errorMessage", "예약번호가 존재하지 않습니다.");
	    }
	    return "resMod";
	}

	@PostMapping("/res/update")
	public String updateRes(@RequestParam(value = "resNumber") String resNumber,
							@RequestParam(value = "carNumber") String carNumber,
							@RequestParam(value = "resDate") LocalDate resDate,
							@RequestParam(value = "useBeginDate") LocalDate useBeginDate,
							@RequestParam(value = "returnDate") LocalDate returnDate,
							@RequestParam(value = "price") double price,
							Model model) {
		try {
			resService.ModifyRes(resNumber, carNumber, resDate, useBeginDate, returnDate, price);
			model.addAttribute("successMessage", "예약 정보가 수정되었습니다.");

		}

		catch (Exception e) {
			model.addAttribute("errorMessage", "예약 수정에 실패했습니다..");
		}
		return "resMod";
	}
	
 	// 예약 삭제 페이지
 	@GetMapping("/res/delete")
 	public String delRes() {
 		return "resDel";
 	}
	
 	@PostMapping("/res/delete")
	public String delRes(@RequestParam(value = "resNumber") String resNumber, Model model) {
	    try {
	         if (resService.existResNum(resNumber)) {
	             	resService.deleteResResNum(resNumber);
	             	model.addAttribute("successMessage", "예약이 삭제되었습니다.");
	         }

	         else {
	             	model.addAttribute("errorMessage", "예약 정보가 존재하지 않습니다.");
	         }
	    }

		catch (Exception e) {
	        model.addAttribute("errorMessage", "예약 삭제에 실패했습니다..");
	    }
	      return "resDel";
	}




	// 매출 관련 페이지
	@GetMapping("/revenue")
	public String revunueMenu() {
		return "revenueForm";
	}

	@PostMapping("/revenue/revenueForm")
	public String calRevenue(@RequestParam(value = "year", required = false) Integer year,
							 @RequestParam(value = "month", required = false) Integer month,
							 Model model) {
		try {
			List<Res> resList = resService.loadAllRes();

			if (year != null && month != null) {
				// 선택된 연도와 월에 해당하는 예약 정보 필터링
				resList = resList.stream()
						.filter(res -> res.getResDate().getYear() == year && res.getResDate().getMonthValue() == month)
						.collect(Collectors.toList());
			}

			else if (year != null) {
				// 연도만 입력된 경우 해당 연도의 예약 정보 필터링
				resList = resList.stream()
						.filter(res -> res.getResDate().getYear() == year)
						.collect(Collectors.toList());
			}

			else if (month != null) {
				// 월만 입력된 경우 오류 메시지 출력
				model.addAttribute("errorMessage", "년도를 입력하지 않으면 매출을 조회할 수 없습니다.");
				return "revenueForm";
			}

			// 선택된 기간의 매출 계산
			double totalRevenue = resList.stream()
					.mapToDouble(Res::getPrice)
					.sum();

			// 모델에 연도, 월, 매출 추가
			model.addAttribute("year", year);
			model.addAttribute("month", month);
			model.addAttribute("totalRevenue", totalRevenue);

			// 매출 조회 페이지로 복귀
			return "revenueForm";
		}

		catch (Exception e) {
			// 오류 발생 시 오류 메시지를 모델에 추가
			model.addAttribute("errorMessage", "매출 조회 중 오류가 발생했습니다.");

			// 매출 조회 페이지로 다시 복귀
			return "revenueForm";
		}
	}



    // 버전 페이지
	@GetMapping("/version")
	public String version() {
		return "version";
	}

	// 차 종류 페이지
	@GetMapping("/carType")
	public String carType() {
		return "carType";
	}
	
}
