#
# oci-compute-control-python version 1.0.
#
# Copyright (c) 2020 Oracle, Inc.
# Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
#

import io
import json
import oci

from fdk import response

def handler(ctx, data: io.BytesIO=None):
    pool_ocid = 'ocid1.nodepool.oc1.iad.aaaaaaaaaftgmzrxgbsgimlemi4giyjzmq4toy3cgntgemddgnstqnjrgfsg'
    signer = oci.auth.signers.get_resource_principals_signer()
    oke_client = oci.container_engine.ContainerEngineClient(config={}, signer=signer)
    pool = oke_client.get_node_pool(pool_ocid)
    sizeNuevo = pool.data.node_config_details.size + 1
    node_config_details = oci.container_engine.models.UpdateNodePoolNodeConfigDetails(size=sizeNuevo)
    update_node_pool_details = oci.container_engine.models.UpdateNodePoolDetails(node_config_details=node_config_details)
    ce_composite_ops = oci.container_engine.ContainerEngineClientCompositeOperations(oke_client)
    resp = ce_composite_ops.update_node_pool_and_wait_for_state(pool_ocid,
                                                                update_node_pool_details,
                                                                wait_for_states=[oci.container_engine.models.WorkRequest.STATUS_SUCCEEDED
,
                                                                 oci.container_engine.models.WorkRequest.STATUS_FAILED])

    return response.Response(
        ctx, 
        response_data=json.dumps({"status": "{0}".format(resp)}),
        headers={"Content-Type": "application/json"}
    )
