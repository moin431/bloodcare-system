package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.BloodStock;
import bd.edu.seu.bdms.repository.BloodStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodStockService {
    private final BloodStockRepository bloodStockRepository;

    public BloodStockService(BloodStockRepository bloodStockRepository) {
        this.bloodStockRepository = bloodStockRepository;
    }
    public BloodStock findByGroup(String group){
        return bloodStockRepository.findByBloodGroup(group);
    }
    public List<BloodStock> findAll(){
        return bloodStockRepository.findAll();
    }
    public void saveStock(String group, int unit){

        BloodStock bloodStock = bloodStockRepository.findByBloodGroup(group);

        if(bloodStock != null){
            bloodStock.setUnits(bloodStock.getUnits() + unit);
        }else{
            bloodStock = new BloodStock();
            bloodStock.setBloodGroup(group);
            bloodStock.setUnits(unit);
        }

        bloodStockRepository.save(bloodStock);
    }
    public int getTotalUnits(){
        return bloodStockRepository.findAll()
                .stream()
                .mapToInt(BloodStock::getUnits)
                .sum();
    }

    public long getTotalBloodUnits() {
        return bloodStockRepository.count();
    }
}
