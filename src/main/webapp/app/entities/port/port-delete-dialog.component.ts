import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPort } from 'app/shared/model/port.model';
import { PortService } from './port.service';

@Component({
  templateUrl: './port-delete-dialog.component.html',
})
export class PortDeleteDialogComponent {
  port?: IPort;

  constructor(protected portService: PortService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.portService.delete(id).subscribe(() => {
      this.eventManager.broadcast('portListModification');
      this.activeModal.close();
    });
  }
}
