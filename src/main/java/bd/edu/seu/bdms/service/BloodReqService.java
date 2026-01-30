package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.BloodRequest;
import bd.edu.seu.bdms.model.BloodStock;
import bd.edu.seu.bdms.repository.BloodReqRepository;
import bd.edu.seu.bdms.repository.BloodStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodReqService {
    private final BloodReqRepository bloodReqRepository;

    public BloodReqService(BloodReqRepository bloodReqRepository) {
        this.bloodReqRepository = bloodReqRepository;
    }

    public BloodRequest saveRequest(BloodRequest bloodRequest) {
        bloodRequest.setStatus("PENDING");
        return bloodReqRepository.save(bloodRequest);
    }
    public List<BloodRequest> getAllRequests(){
        return bloodReqRepository.findAll();
    }

    public long countRequests(){
        return bloodReqRepository.count();
    }
    public void approveRequest(String id){
        BloodRequest bloodRequest = bloodReqRepository.findById(id).orElse(null);
        if(bloodRequest!=null){
            bloodRequest.setStatus("APPROVED");
            bloodReqRepository.save(bloodRequest);
        }
    }
    public void rejectRequest(String id){
        BloodRequest bloodRequest = bloodReqRepository.findById(id).orElse(null);
        if(bloodRequest!=null){
            bloodRequest.setStatus("REJECTED");
            bloodReqRepository.save(bloodRequest);
        }
    }
    public long countByStatus(String status){
        return bloodReqRepository.countByStatus(status);
    }
}
