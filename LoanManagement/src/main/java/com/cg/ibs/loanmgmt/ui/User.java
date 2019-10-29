package com.cg.ibs.loanmgmt.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ibs.loanmgmt.IBSexception.ExceptionMessages;
import com.cg.ibs.loanmgmt.IBSexception.IBSException;
import com.cg.ibs.loanmgmt.bean.LoanMaster;
import com.cg.ibs.loanmgmt.bean.LoanType;
import com.cg.ibs.loanmgmt.bean.LoanTypeBean;
import com.cg.ibs.loanmgmt.service.CustomerService;
import com.cg.ibs.loanmgmt.service.CustomerServiceImpl;

public class User implements ExceptionMessages {
	static Scanner read = new Scanner(System.in);
	private LoanMaster loanMaster;
	private LoanTypeBean loanType;
	private static CustomerService customerService = new CustomerServiceImpl();

	public void userLogin() throws IBSException {
		UserOptions choice = null;
		while (choice != UserOptions.EXIT) {
			System.out.println("Login as an existing customer or admin? ");
			System.out.println("------------------------");
			for (UserOptions menu : UserOptions.values()) {
				System.out.println((menu.ordinal() + 1) + "\t" + menu);
			}
			String userLoginInput = read.next();
			Pattern pattern = Pattern.compile("[0-9]{1}");
			Matcher matcher = pattern.matcher(userLoginInput);
			if (matcher.matches()) {
				int ordinal = Integer.parseInt(userLoginInput);
				if (ordinal >= 1 && ordinal < (UserOptions.values().length) + 1) {
					choice = UserOptions.values()[ordinal - 1];
					switch (choice) {
					case VISITOR:
						selectLoanType();
						break;
					case EXISTING_CUSTOMER:
						init();
						break;
					case BANK_ADMIN:
						adminInit();
						break;
					case EXIT:
						System.out.println("\nThank You For Visiting. \nHave a nice day!");
						break;
					}
				} else {
					choice = null;
					try {
						if (choice == null) {
							throw new IBSException(ExceptionMessages.MESSAGEFORWRONGINPUT);
						}
					} catch (IBSException exp) {
//						LOGGER.info("User is giving wrong input");
						System.out.println(exp.getMessage());

					}

				}
			} else {
				try {
					throw new IBSException(ExceptionMessages.MESSAGEFORINPUTMISMATCH);
				} catch (IBSException exp) {
					System.out.println(exp.getMessage());
				}
			}
		}
	}

	public void init() throws IBSException {
		CustomerOptions customerChoice = null;
		while (customerChoice != CustomerOptions.EXIT) {
			System.out.println("You've entered as an Existing customer :");
			System.out.println("--------------------");
			System.out.println("Please select one of the following to proceed further : ");
			System.out.println("--------------------");
			for (CustomerOptions menu : CustomerOptions.values()) {
				System.out.println((menu.ordinal() + 1) + ".\t" + menu);
			}
			System.out.println("Choice");
			String customerLoginInput = read.next();
			Pattern pattern = Pattern.compile("[0-9]{1}");
			Matcher matcher = pattern.matcher(customerLoginInput);
			if (matcher.matches()) {
				int ordinal = Integer.parseInt(customerLoginInput);
				if (ordinal >= 1 && ordinal < (CustomerOptions.values().length) + 1) {
					customerChoice = CustomerOptions.values()[ordinal - 1];
					switch (customerChoice) {
					case APPLY_LOAN:
						selectLoanType();
						break;
					case PAY_EMI:
						System.out.println("Enter the Loan Account Number:");
						BigInteger loanAccNum = read.nextBigInteger();
						payEMI(loanAccNum);
						break;
					case APPLY_PRECLOSURE:
						System.out.println("Enter the Loan Account Number:");
						BigInteger loanAccNum1 = read.nextBigInteger();
						applyPreClosure(loanAccNum1);
						break;
					case VIEW_HISTORY:
						System.out.println(" Enter your customer ID: ");
						getLoanDetails(read.next());
						break;
					case EXIT:
						System.out.println("Thank You! Come Again.");
						userLogin();
					}

				} else {
					customerChoice = null;
					try {
						if (customerChoice == null)

							throw new IBSException(ExceptionMessages.MESSAGEFORWRONGINPUT);
					} catch (IBSException exp) {
						System.out.println(exp.getMessage());
					}
				}
			} else {
				try {
					throw new IBSException(ExceptionMessages.MESSAGEFORINPUTMISMATCH);
				} catch (IBSException exp) {
					System.out.println(exp.getMessage());
				}
			}
		}
	}

	private void payEMI(BigInteger loanAccNum) {
		loanMaster = new LoanMaster();
		if (customerService.verifyLoanExist(loanAccNum)) {
			System.out.println("Loan doesn't exist for the provided loan number.");
		} else if (customerService.verifyEmiApplicable(loanAccNum)) {
			System.out.println("Loan has been closed for the provided loan number.");
		} else {
			loanMaster = customerService.getLoanDetails(loanAccNum);
			System.out.println("Following are the EMI details for the provided loan number.");
			System.out.println("Total number of EMIs to be paid: " + loanMaster.getTotalNumOfEmis());
			System.out.println("Number of EMIs paid: " + loanMaster.getNumOfEmisPaid());
			System.out.println("Last date for paying the next EMI: " + loanMaster.getNextEmiDate());
			System.out.println("EMI Amount: " + loanMaster.getEmiAmount());
			System.out.println("Balance Loan Amount: " + loanMaster.getBalance());
			System.out.println("Do you want to pay you next EMI ? \n1. Yes\n2. No");
			int emiPaymentChoice = read.nextInt();
			switch (emiPaymentChoice) {
			case 1:
				System.out.println("EMI amount deducted. Thank you!");
				System.out.println(
						"Balance has been updated. New balance: INR " + customerService.calculateBalance(loanMaster));
				customerService.updateEMI(loanMaster);
				System.out.println("Next EMI Date: " + loanMaster.getNextEmiDate());
				System.out.println("Number of EMIs to be paid: " + loanMaster.getNumOfEmisPaid());
				customerService.updateBalance(loanMaster);
			case 2:
				System.out.println("Have a nice day !");
			}

			System.out.println(loanMaster.getNumOfEmisPaid());
		}

	}

	private void applyPreClosure(BigInteger loanAccNum) {
		loanMaster = new LoanMaster();
		if (customerService.verifyLoanExist(loanAccNum)) {
			System.out.println("Loan doesn't exist for the provided loan number.");
		} else if (customerService.verifyPreClosureApplicable(loanAccNum)) {
			System.out.println("Loan is not applicable for Pre-Closure Application.");
		} else {
			loanMaster = customerService.getLoanDetails(loanAccNum);
			System.out.println("Following are the PreClosure details for the provided loan number: ");
			System.out.println("Number of EMIs left to be paid: "
					+ (loanMaster.getTotalNumOfEmis() - loanMaster.getNumOfEmisPaid()));
			System.out.println("Remaining balance: " + loanMaster.getBalance());
			System.out.println("Do you want to close the loan by paying the pending balance ?\n1. Yes\n2.No");
			int preClosurePaymentChoice = read.nextInt();
			switch (preClosurePaymentChoice) {
			case 1:
				System.out.println(
						"Loan details for the applied pre-closure have been sent for verification to the bank.");
				customerService.updatePreClosure(loanMaster);

			case 2:
				System.out.println("Have a nice day !");
			}
		}
	}

	private void selectLoanType() throws IBSException {
		LoanTypes choice = null;
		while (choice != LoanTypes.GO_BACK) {
			System.out.println("Menu");
			System.out.println("--------------------");
			System.out.println("Choice");
			System.out.println("--------------------");
			for (LoanTypes menu : LoanTypes.values()) {
				System.out.println((menu.ordinal() + 1) + "\t" + menu);
			}
			System.out.println("Choice");
			String customerLoginInput = read.next();
			Pattern pattern = Pattern.compile("[0-9]{1}");
			Matcher matcher = pattern.matcher(customerLoginInput);
			if (matcher.matches()) {
				int ordinal = Integer.parseInt(customerLoginInput);
				if (ordinal >= 1 && ordinal < (LoanTypes.values().length) + 1) {
					choice = LoanTypes.values()[ordinal - 1];

					switch (choice) {
					case HOME_LOAN:

						break;
					case EDUCATION_LOAN:

						break;
					case PERSONAL_LOAN:

						break;
					case VEHICLE_LOAN:

						break;
					case GO_BACK:
						System.out.println("Thank You!");
						break;
					}
				} else {
					choice = null;
					try {
						if (choice == null) {
							throw new IBSException(ExceptionMessages.MESSAGEFORWRONGINPUT);
						}
					} catch (IBSException exp) {
						System.out.println(exp.getMessage());

					}
				}

			} else {
				try {
					throw new IBSException(ExceptionMessages.MESSAGEFORINPUTMISMATCH);
				} catch (IBSException exp) {
					System.out.println(exp.getMessage());
				}
			}

		}
	}

	public void adminInit() throws IBSException {
		AdminOptions adminChoice = null;
		while (adminChoice != AdminOptions.GO_BACK) {
			System.out.println("Menu");
			System.out.println("--------------------");
			System.out.println("Choice");
			System.out.println("--------------------");
			for (AdminOptions menu : AdminOptions.values()) {
				System.out.println((menu.ordinal() + 1) + "\t" + menu);
			}
			System.out.println("Choice");
			int ordinal = read.nextInt();
			if (ordinal >= 1 && ordinal < (AdminOptions.values().length) + 1) {
				adminChoice = AdminOptions.values()[ordinal - 1];
				switch (adminChoice) {
				case VERIFY_LOAN:
					verifyLoan();
					break;
				case VERIFY_PRECLOSURE:
					verifyPreClosure();
					break;
				case GO_BACK:
					userLogin();
				}

			} else {
				adminChoice = null;
				try {
					if (adminChoice == null) {
						throw new IBSException(ExceptionMessages.MESSAGEFORWRONGINPUT);
					}
				} catch (IBSException exp) {
					System.out.println(exp.getMessage());

				}

			}
		}
	}

	private void verifyPreClosure() {
		
		
	}

	private void verifyLoan() {

	}

}
