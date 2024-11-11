package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class KredytBB {
	private int lo;
	private int yr;
	private double pe;
	private Integer loan;
	private Integer years;
	private Double perc;
	private Double result;

	@Inject
	FacesContext ctx;


	public Integer getLoan() {
		return loan;
	}

	public void setLoan(Integer loan) {
		this.loan = loan;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Double getPerc() {
		return perc;
	}

	public void setPerc(Double perc) {
		this.perc = perc;
	}

	public Double getResult() {
		return result;
	}
	
	public void setResult(Double result) {
		this.result = result;
	}

	public boolean doTheMath() {
		try {

			lo = loan;
			yr = years;
			pe = perc;
	
			
			double months = yr * 12;
			double ods = lo * yr * (pe/100);
			double all = lo + ods;
			result = all/months;
			
			result = Math.round(result * 100.0) / 100.0;
			
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
			
		} catch (Exception e) {				
			return false;
		}
	}

	// Go to "showresult" if ok
		public String calc() {
			if (doTheMath()) {
				return "showresult";
			}
			return null;
		}

		// Put result in messages on AJAX call
		public String calc_AJAX() {
			if (doTheMath()) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Miesięczna rata to: " + result + "zł", null));
			}
			return null;
		}

		public String info() {
			return "info";
		}
		
		
	}