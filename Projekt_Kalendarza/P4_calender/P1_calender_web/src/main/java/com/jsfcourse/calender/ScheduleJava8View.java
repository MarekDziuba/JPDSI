package com.jsfcourse.calender;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.event.schedule.ScheduleEntryResizeEvent;
import org.primefaces.event.schedule.ScheduleRangeEvent;
import org.primefaces.model.*;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.UserEventDAO;
import com.jsf.dao.UserDataDAO;
import com.jsf.entities.UserEvent;
import com.jsf.entities.UserData;
import com.jsfcourse.calender.ExtenderService.ExtenderExample;

@Named
@ViewScoped
public class ScheduleJava8View implements Serializable {
	
	private UserEvent userEvent = new UserEvent();
	private UserEvent loaded = null;
	
	
	
	//Pobiera waroisci z bazy danych
	@EJB
	UserEventDAO userEventDAO;
	
	public UserEvent getUserEvent() {
		return userEvent;
	}


    @Inject
    private ExtenderService extenderService;

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    private boolean slotEventOverlap = true;
    private boolean showWeekNumbers = false;
    private boolean showHeader = true;
    private boolean draggable = true;
    private boolean resizable = true;
    private boolean selectable = false;
    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;
    private boolean rtl = false;

    private double aspectRatio = Double.MIN_VALUE;

    private String leftHeaderTemplate = "prev,next today";
    private String centerHeaderTemplate = "title";
    private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
    private String nextDayThreshold = "09:00:00";
    private String weekNumberCalculation = "local";
    private String weekNumberCalculator = "date.getTime()";
    private String displayEventEnd;
    private String timeFormat;
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String slotLabelFormat = "HH:mm";
    private String scrollTime = "06:00:00";
    private String minTime = "04:00:00";
    private String maxTime = "20:00:00";
    private String locale = "en";
    private String serverTimeZone = ZoneId.systemDefault().toString();
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";
    private String view = "timeGridWeek";
    private String height = "auto";

    private String extenderCode = "// Write your code here or select an example from above";
    private String selectedExtenderExample = "";

    private Map<String, ExtenderExample> extenderExamples;

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        Integer loggedUserId = (Integer) session.getAttribute("loggedUserId");

        if (loggedUserId != null) {
            List<UserEvent> userEvents = userEventDAO.getUserEventsByUserId(loggedUserId);
            for (UserEvent userEvent : userEvents) {
                DefaultScheduleEvent<?> scheduleEvent = DefaultScheduleEvent.builder()
                        .id(String.valueOf(userEvent.getId_events())) // Ustawienie ID jako String
                        .title(userEvent.getName_event())
                        .startDate(userEvent.getStart_date_event().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .endDate(userEvent.getEnd_date_event().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .description(userEvent.getDescription())
                        .borderColor("blue")
                        .build();

                eventModel.addEvent(scheduleEvent);
            }
        }
    }


    
    public ExtenderService getScheduleExtenderService() {
        return extenderService;
    }

    public void setScheduleExtenderService(ExtenderService extenderService) {
        this.extenderService = extenderService;
    }

    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    
    
    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    
    public void addEvent() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            // Dodawanie nowego wydarzenia
            UserEvent userEvent = new UserEvent();
            userEvent.setName_event(event.getTitle());
            userEvent.setStart_date_event(Date.from(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
            userEvent.setEnd_date_event(Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
            userEvent.setDescription(event.getDescription());

            // Pobierz zalogowanego użytkownika
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
            Integer loggedUserId = (Integer) session.getAttribute("loggedUserId");

            if (loggedUserId != null) {
                UserData userData = new UserData();
                userData.setId_user(loggedUserId);
                userEvent.setUserData(userData);

                // Zapisz nowe wydarzenie w bazie
                userEventDAO.create(userEvent);

                // Dodaj wydarzenie do modelu
                DefaultScheduleEvent<?> newEvent = DefaultScheduleEvent.builder()
                        .id(String.valueOf(userEvent.getId_events())) // Ustaw ID nowego wydarzenia
                        .title(userEvent.getName_event())
                        .startDate(event.getStartDate())
                        .endDate(event.getEndDate())
                        .description(event.getDescription())
                        .borderColor("green")
                        .build();

                eventModel.addEvent(newEvent);
            }
        } else {
            // Edycja istniejącego wydarzenia
            int eventId = Integer.parseInt(event.getId());
            UserEvent existingEvent = userEventDAO.find(eventId);

            if (existingEvent != null) {
                existingEvent.setName_event(event.getTitle());
                existingEvent.setStart_date_event(Date.from(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
                existingEvent.setEnd_date_event(Date.from(event.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
                existingEvent.setDescription(event.getDescription());

                // Zapisz zmiany w bazie
                userEventDAO.merge(existingEvent);

                // Zaktualizuj wydarzenie w modelu
                eventModel.updateEvent(event);
            }
        }

        // Zresetuj formularz po zapisaniu
        event = new DefaultScheduleEvent<>();
    }



    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        ScheduleEvent<?> selectedEvent = selectEvent.getObject();

        if (selectedEvent.getId() != null) {
            try {
                // Pobierz dane wydarzenia z bazy danych
                int eventId = Integer.parseInt(selectedEvent.getId());
                UserEvent loaded = userEventDAO.find(eventId);

                if (loaded != null) {
                    // Wypełnij formularz danymi z bazy
                    event = DefaultScheduleEvent.builder()
                            .id(String.valueOf(loaded.getId_events()))
                            .title(loaded.getName_event())
                            .startDate(loaded.getStart_date_event().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            .endDate(loaded.getEnd_date_event().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            .description(loaded.getDescription())
                            .build();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Event not found in database!", null));
                }
            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid event ID format!", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Event ID is null!", null));
        }
    }




    public void onViewChange(SelectEvent<String> selectEvent) {
        view = selectEvent.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "View Changed", "View:" + view);
        addMessage(message);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        String eventId = event.getScheduleEvent().getId(); // Pobierz ID z kalendarza

        if (eventId != null) {
            try {
                // Przekształć ID na liczbę całkowitą
                int parsedEventId = Integer.parseInt(eventId);

                // Pobierz wydarzenie z bazy na podstawie ID
                UserEvent userEvent = userEventDAO.find(parsedEventId);

                if (userEvent != null) {
                    // Aktualizuj daty rozpoczęcia i zakończenia
                    userEvent.setStart_date_event(Date.from(event.getScheduleEvent().getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
                    userEvent.setEnd_date_event(Date.from(event.getScheduleEvent().getEndDate().atZone(ZoneId.systemDefault()).toInstant()));

                    // Zapisz zmiany w bazie danych
                    userEventDAO.merge(userEvent);

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Updated in database.");
                    addMessage(message);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Event not found in database!", null));
                }
            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid event ID format: " + eventId, null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Event ID is null!", null));
        }
    }



    public void onEventResize(ScheduleEntryResizeEvent event) {
        UserEvent userEvent = userEventDAO.find(event.getScheduleEvent().getId());
        if (userEvent != null) {
            userEvent.setEnd_date_event(Date.from(event.getScheduleEvent().getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
            userEventDAO.merge(userEvent);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Updated in database.");
            addMessage(message);
        }
    }


    public void onRangeSelect(ScheduleRangeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Range selected",
                "Start-Date:" + event.getStartDate() + ", End-Date: " + event.getEndDate());

        addMessage(message);
    }

    public void onEventDelete() {
        String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
        if (eventId != null) {
            UserEvent userEvent = userEventDAO.find(eventId);
            if (userEvent != null) {
                userEventDAO.remove(userEvent);

                // Usuń wydarzenie z modelu
                eventModel.deleteEvent(eventModel.getEvent(eventId));

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted", "Removed from database.");
                addMessage(message);
            }
        }
    }


    public void onExtenderExampleSelect(AjaxBehaviorEvent event) {
        ExtenderExample example = getExtenderExample();
        if (!"custom".equals(selectedExtenderExample) && example != null) {
            if (example.getDetails() != null && !example.getDetails().isEmpty()) {
                FacesMessage message = new FacesMessage(example.getName(), example.getDetails());
                FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), message);
            }
            this.extenderCode = example.getValue();
        }
    }
    
    public void deleteEvent() {
        if (event.getId() != null) {
            try {
                // Pobierz ID wydarzenia do usunięcia
                int eventId = Integer.parseInt(event.getId());
                UserEvent userEvent = userEventDAO.find(eventId);

                if (userEvent != null) {
                    // Usuń wydarzenie z bazy danych
                    userEventDAO.remove(userEvent);

                    // Usuń wydarzenie z modelu kalendarza
                    eventModel.deleteEvent(event);

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted", "Event successfully removed.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Event not found", "Event could not be found in the database.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } catch (NumberFormatException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid event ID", "The event ID format is invalid.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No event selected", "Please select an event to delete.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        // Reset event po usunięciu
        event = new DefaultScheduleEvent<>();
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean isShowWeekends() {
        return showWeekends;
    }

    public void setShowWeekends(boolean showWeekends) {
        this.showWeekends = showWeekends;
    }

    public boolean isSlotEventOverlap() {
        return slotEventOverlap;
    }

    public void setSlotEventOverlap(boolean slotEventOverlap) {
        this.slotEventOverlap = slotEventOverlap;
    }

    public boolean isShowWeekNumbers() {
        return showWeekNumbers;
    }

    public void setShowWeekNumbers(boolean showWeekNumbers) {
        this.showWeekNumbers = showWeekNumbers;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isRtl() {
        return rtl;
    }

    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }

    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    public void setAllDaySlot(boolean allDaySlot) {
        this.allDaySlot = allDaySlot;
    }

    public double getAspectRatio() {
        return aspectRatio == 0 ? Double.MIN_VALUE : aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getLeftHeaderTemplate() {
        return leftHeaderTemplate;
    }

    public void setLeftHeaderTemplate(String leftHeaderTemplate) {
        this.leftHeaderTemplate = leftHeaderTemplate;
    }

    public String getCenterHeaderTemplate() {
        return centerHeaderTemplate;
    }

    public void setCenterHeaderTemplate(String centerHeaderTemplate) {
        this.centerHeaderTemplate = centerHeaderTemplate;
    }

    public String getRightHeaderTemplate() {
        return rightHeaderTemplate;
    }

    public void setRightHeaderTemplate(String rightHeaderTemplate) {
        this.rightHeaderTemplate = rightHeaderTemplate;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNextDayThreshold() {
        return nextDayThreshold;
    }

    public void setNextDayThreshold(String nextDayThreshold) {
        this.nextDayThreshold = nextDayThreshold;
    }

    public String getWeekNumberCalculation() {
        return weekNumberCalculation;
    }

    public void setWeekNumberCalculation(String weekNumberCalculation) {
        this.weekNumberCalculation = weekNumberCalculation;
    }

    public String getWeekNumberCalculator() {
        return weekNumberCalculator;
    }

    public void setWeekNumberCalculator(String weekNumberCalculator) {
        this.weekNumberCalculator = weekNumberCalculator;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }

    public String getSlotLabelInterval() {
        return slotLabelInterval;
    }

    public void setSlotLabelInterval(String slotLabelInterval) {
        this.slotLabelInterval = slotLabelInterval;
    }

    public String getSlotLabelFormat() {
        return slotLabelFormat;
    }

    public void setSlotLabelFormat(String slotLabelFormat) {
        this.slotLabelFormat = slotLabelFormat;
    }

    public String getDisplayEventEnd() {
        return displayEventEnd;
    }

    public void setDisplayEventEnd(String displayEventEnd) {
        this.displayEventEnd = displayEventEnd;
    }

    public String getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(String scrollTime) {
        this.scrollTime = scrollTime;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getClientTimeZone() {
        return clientTimeZone;
    }

    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    public String getColumnHeaderFormat() {
        return columnHeaderFormat;
    }

    public void setColumnHeaderFormat(String columnHeaderFormat) {
        this.columnHeaderFormat = columnHeaderFormat;
    }

    public ExtenderExample getExtenderExample() {
        return extenderExamples.get(selectedExtenderExample);
    }

    public String getSelectedExtenderExample() {
        return selectedExtenderExample;
    }

    public void setSelectedExtenderExample(String selectedExtenderExample) {
        this.selectedExtenderExample = selectedExtenderExample;
    }

    public String getExtenderCode() {
        return extenderCode;
    }

    public void setExtenderCode(String extenderCode) {
        this.extenderCode = extenderCode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<SelectItem> getExtenderExamples() {
        return extenderExamples.values().stream() //
                .sorted(Comparator.comparing(ExtenderExample::getName)) //
                .map(example -> new SelectItem(example.getKey(), example.getName())) //
                .collect(Collectors.toList());
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }
}