#!/usr/bin/env python3
import sys
from zkay.cloak_frontend import transaction_benchmark_ctx

# Scenario
with transaction_benchmark_ctx(sys.argv[1]) as g:
	pc_addr, r1_addr, r2_addr, r3_addr, author_addr = g.create_dummy_accounts(5)

	pc = g.deploy(r1_addr, r2_addr, r3_addr, user=pc_addr)
	r1 = g.connect(pc.address, user=r1_addr)
	r2 = g.connect(pc.address, user=r2_addr)
	r3 = g.connect(pc.address, user=r3_addr)
	author = g.connect(pc.address, user=author_addr)

	author.registerPaper(1234)
	r1.recordReview(1234, 4)
	r2.recordReview(1234, 2)
	r3.recordReview(1234, 1)
	pc.decideAcceptance(author_addr)
