package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
// TODO should we add path {users}
// TODO can we configure the requestparam to not need user
@RequestMapping("/transfers/")
@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;


    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

   // @PreAuthorize("hasRole('USER')")
    @ApiOperation("Create Transfer")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createTransfer(@ApiParam("enter the sender")@RequestParam String sender, @ApiParam("enter the receiver")@RequestParam String receiver, @ApiParam("enter the amount")@RequestParam String amount){
        if(!transferDao.createTransfer(sender, receiver, amount)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create Transfer Failed");
        }
    }
    //Transfer getTransferById(int id, String username);
    @ApiOperation("Get Transfer By ID")
    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@ApiParam("enter the transfer id") @PathVariable int id, Principal principal) {
        Transfer transfer = transferDao.getTransferById(id, principal.getName());
        if (transfer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer Not Found");
        }
        return transfer;
    }
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("Get All Transfers")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal){
        return transferDao.findAllTransfers(principal.getName());
    }
//    @ApiOperation("Get All Pending Transfers")
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public List<Transfer> getAllPendingTransfers(@ApiParam("enter username")@RequestParam String username){
//        return transferDao.findAllPendingTransfers(username);
//        List<Transfer> pendingTransfers
//    }
    @ApiOperation("Update Transfer Status")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateTransferStatus(@ApiParam("enter transfer id") @PathVariable int id, @ApiParam("enter new status") @RequestParam String status, @ApiParam("enter the sender") @RequestParam String sender){
        if (!transferDao.updateTransferStatus(id, status, sender)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Update For Transfer");
        }
    }

}
// pending - new transfer
// approved - receiver accepts could be false if transfer cannot be made
// rejected  - receiver rejects always true
