import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from './shipment.service';

@Component({
  templateUrl: './shipment-delete-dialog.component.html',
})
export class ShipmentDeleteDialogComponent {
  shipment?: IShipment;

  constructor(protected shipmentService: ShipmentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shipmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shipmentListModification');
      this.activeModal.close();
    });
  }
}
