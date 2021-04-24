public interface IObserver {
    void update(Model model);
    void updateTime(Model model);
    void updateAfterDelete(Model model);
    void updateAfterRinging(Model model);
}
